from locust import HttpLocust, TaskSet, task
import random
import http.client
import json

carAppHeaders = None


def get(l, url):
    global carAppHeaders
    return l.client.get(url, headers=carAppHeaders, catch_response=True)


def post(l, url, body):
    global carAppHeaders
    return l.client.post(url, data=body, headers=carAppHeaders, catch_response=True)


def put(l, url, body):
    global carAppHeaders
    return l.client.put(url, data=body, headers=carAppHeaders, catch_response=True)


def delete(l, url):
    global carAppHeaders
    return l.client.delete(url, headers=carAppHeaders, catch_response=True)


def handleResponse(response, expectedCode):
    if response.status_code == expectedCode:
        response.success()
    else:
        response.failure("Wrong status code: " +
                         str(response.status_code) + " - " + response.text)


def create(l):
    brandBody = {"id": "", "name": "Locust"}
    countryBody = {"id": "", "name": "Locust"}
    carBody = {"id": "", "registration": "1980-01-01T10:00:00",
               "brand": {"id": ""}, "country": {"id": ""}}
    with post(l, "/car-api/api/brands/", json.dumps(brandBody)) as responseBrand:
        if responseBrand.status_code != 201:
            return [responseBrand, brandBody, countryBody, carBody]
        handleResponse(responseBrand, 201)
        responseJson = json.loads(responseBrand.text)
        brandBody['id'] = responseJson['id']
        carBody['brand']['id'] = responseJson['id']
        with post(l, "/car-api/api/countries/", json.dumps(countryBody)) as responseCountry:
            if responseCountry.status_code != 201:
                return [responseCountry, brandBody, countryBody, carBody]
            handleResponse(responseCountry, 201)
            responseJson = json.loads(responseCountry.text)
            countryBody['id'] = responseJson['id']
            carBody['country']['id'] = responseJson['id']
            with post(l, "/car-api/api/cars/", json.dumps(carBody)) as responseCar:
                if responseCar.status_code != 201:
                    return [responseCar, brandBody, countryBody, carBody]
                carBody['id'] = json.loads(responseCar.text)['id']
                return [responseCar, brandBody, countryBody, carBody]


def update(l, brandBody, countryBody, carBody):
    brandBody['name'] = "LocustUpdate"
    countryBody['name'] = "LocustUpdate"
    carBody['registration'] = "2019-01-01T10:00:00"
    with put(l, "/car-api/api/brands/" + str(brandBody['id']), json.dumps(brandBody)) as responseBrand:
        if responseBrand.status_code != 200:
            return [responseBrand, brandBody, countryBody, carBody]
        handleResponse(responseBrand, 200)
        with put(l, "/car-api/api/countries/" + str(countryBody['id']), json.dumps(countryBody)) as responseCountry:
            if responseCountry.status_code != 200:
                return [responseCountry, brandBody, countryBody, carBody]
            handleResponse(responseCountry, 200)
            with put(l, "/car-api/api/cars/" + str(carBody['id']), json.dumps(carBody)) as responseCar:
                return [responseCar, brandBody, countryBody, carBody]


def deletePhase(l, brandBody, countryBody, carBody):
    with delete(l, "/car-api/api/cars/" + str(carBody['id'])) as responseCar:
        if responseCar.status_code != 204:
            return responseCar
        handleResponse(responseCar, 204)
        with delete(l, "/car-api/api/brands/" + str(brandBody['id'])) as responseBrand:
            if responseBrand.status_code != 204:
                return responseBrand
            handleResponse(responseBrand, 204)
            return delete(l, "/car-api/api/countries/" + str(countryBody['id']))


def login():
    global carAppHeaders
    if carAppHeaders == None:
        conn = http.client.HTTPSConnection("everis-carapp.eu.auth0.com")
        payload = "{\"client_id\":\"wc9hz6numwWDigRgMI7CZnXm1B4Qn9fW\",\"client_secret\":\"fd_CvQkZexu2fSxWCYm8IkhmLenp_pRBugoxRYi_UFfAxGF_7uiwf-9qlBM_lj4H\",\"audience\":\"https://everis-carapp.herokuapp.com/car-api\",\"grant_type\":\"client_credentials\"}"
        headers = {'content-type': "application/json"}
        conn.request("POST", "/oauth/token", payload, headers)
        res = conn.getresponse()
        data = res.read()
        token = json.loads(data.decode("utf-8"))
        carAppHeaders = {
            "Authorization": token['token_type'] + " " + token['access_token'],
            "Content-Type": "application/json"
        }


class GetDataBehaviour(TaskSet):
    @task(1)
    def getAllCars(self):
        with get(self, "/car-api/api/cars") as response:
            if response.status_code == 200:
                response.success()

    @task(2)
    def getOneCar(self):
        with get(self, "/car-api/api/cars/123") as response:
            if response.status_code == 200 or response.status_code == 404:
                response.success()


class EntityLifeCycleBehaviour(TaskSet):
    @task(1)
    def createUpdateDeleteCycle(self):
        response, brandBody, countryBody, carBody = create(self)
        handleResponse(response, 201)
        response, brandBody, countryBody, carBody = update(
            self, brandBody, countryBody, carBody)
        handleResponse(response, 200)
        response = deletePhase(self, brandBody, countryBody, carBody)
        handleResponse(response, 204)


class BackendBehaviour(TaskSet):
    # GetDataBehaviour: 4,
    tasks = {EntityLifeCycleBehaviour: 1}

    def on_start(self):
        login()


class CarAppMicroService(HttpLocust):
    task_set = BackendBehaviour
    min_wait = random.randint(1, 100)
    max_wait = random.randint(100, 1000)
