import time
import pandas as pd
import requests
from flask import Flask, jsonify
from sklearn.tree import DecisionTreeClassifier

app = Flask(__name__)

# function to predict aqi (returns predicted aqi, today's aqi, attributes and temperature values)
def aqi_prediction():
    
    # API call to obtain the actual data from the 'sensors'
    api_key = '0228995cba08ffda9c00a098d63b7efd'
    api_pollution = 'http://api.openweathermap.org/data/2.5/air_pollution'
    api_temp = 'http://api.openweathermap.org/data/2.5/weather'

    # returns a dictionary with today's aqi, today's attributes and temperature values
    def get_api_data(api_pollution, api_temp):
        params = {'appid': api_key, 'lat': 59.3293, 'lon': 18.0686} # long e lat from Stockholm
        
        api_data_aqi = requests.get(api_pollution, params=params).json()
        aqi = api_data_aqi['list'][0]['main']['aqi'] # today's aqi
        attributes = api_data_aqi['list'][0]['components'] # today's sensors values

        api_data_temp = requests.get(api_temp, params=params).json()
        temp = round((float) (api_data_temp['main']['temp']) - 273.15) # today's temperature (converted in Celsius)

        response = {'AQI': [aqi], 'T': [temp]}
        response.update({key.upper(): [value] for key, value in attributes.items()})

        return response

    
    new_dict = get_api_data(api_pollution, api_temp)
    columns = ['AQI', 'T', 'CO', 'NO', 'NO2', 'O3', 'SO2', 'PM25', 'PM10', 'NH3']
    new_data = pd.DataFrame.from_dict(new_dict)
    new_data.columns = columns
    print(new_data)

    # machine learning algorithm to predict aqi

    data = pd.read_excel('stockholmAQI.xlsx')
    data.dropna()

    features = ['AQI', 'CO', 'NO', 'NO2', 'O3', 'SO2', 'PM25', 'PM10', 'NH3'] # we excluded the date clearly
    X = data[features]
    y = data['TOMORROW_AQI']

    rnd = int(time.time()) # to make the seed random

    # Initialize and train the decision tree classifier
    clf = DecisionTreeClassifier(random_state=rnd)
    clf.fit(X, y)

    # Prediction:
    new_data_pred = clf.predict(new_data[features])
    new_data['AQI_P'] = new_data_pred[0]
    
    return new_data.iloc[0].to_dict() # output format: {attribute : value, ...}


# Endpoint for calculating the aqi and in general retrieving data
@app.route('/pred_aqi', methods=['GET'])
def get_aqi():

    global to_return
    to_return = aqi_prediction()
    if to_return is not None:
        return jsonify(to_return), 200 # OK
    else:
        return jsonify({'value': 'error'}), 404 # not found

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001)



