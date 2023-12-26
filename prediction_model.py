import time
import pandas as pd
import requests
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import accuracy_score


data = pd.read_excel('/Users/mrtena/Desktop/stockholmAQI.xlsx')
data.dropna()

features = ['AQI', 'CO', 'NO', 'NO2', 'O3', 'SO2', 'PM25', 'PM10', 'NH3'] # we excluded the date
X = data[features]
y = data['TOMORROW_AQI']

rnd = int(time.time()) # to make the seed random

# Split the dataset in train and test
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.1, random_state=rnd)

# Initialize and train the decision tree classifier
clf = DecisionTreeClassifier(random_state=rnd)
clf.fit(X_train, y_train)
test_pred = clf.predict(X_test)

api_key = "0228995cba08ffda9c00a098d63b7efd"
api_url = "http://api.openweathermap.org/data/2.5/air_pollution"

def get_stockholm_data():
    params = {"appid": api_key, "lat": 59.3293, "lon": 18.0686}
    api_data = requests.get(api_url, params=params).json()
    aqi = api_data['list'][0]['main']['aqi']
    components = api_data['list'][0]['components']
    response = {'AQI': [aqi]}
    response.update({key.upper(): [value] for key, value in components.items()})
    return response

stockholm_data = get_stockholm_data()
columns = ['AQI', 'CO', 'NO', 'NO2', 'O3', 'SO2', 'PM25', 'PM10', 'NH3']
new_data = pd.DataFrame.from_dict(stockholm_data)
new_data.columns = columns
print(new_data.head(1))

#--------------------
# PREDICTIONS
#--------------------

new_data_pred = clf.predict(new_data)
new_data_pred

# print predictions
print("AQI tomorrow:")
print(new_data_pred[0])

# print actual values
print("\nToday data:")
print(new_data.head(1))

# accuracy:
accuracy = accuracy_score(y_test, test_pred)
print(f"Accuracy of Decision Tree Classifier: {accuracy:.2f}")