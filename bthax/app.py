#!/usr/bin/env python3
from flask import Flask
from flask import request
from google.cloud import datastore

app = Flask(__name__)
client = datastore.Client('light-client-228509')

@app.route('/', methods=['POST'])
def store_json():
    json_data = request.get_json()
    units = json_data['units']
    print(units)
    entity = datastore.Entity(key=client.key('test_db'))
    entity.update({
        'units':units
    })
    client.put(entity)
    return 'Hello'

if __name__ == '__main__':
    app.run()