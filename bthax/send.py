import requests
import json

headers = {'content-type': 'application/json'}
string_array = ["DE:AD:BA:BE", "DE:AD:BE:EF"]
data = {
 'units': string_array
}

response = requests.post('https://us-central1-light-client-228509.cloudfunctions.net/store_json',
    data=(json.dumps(data)),
    headers=headers)

if response.status_code == 200:
    print("Boats and Hoes")