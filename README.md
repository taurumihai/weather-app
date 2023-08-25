#                                                             Weather app 

 # Data base configuration 
       Database used for this project is Postgres v11.0. Interaction with DB is mandatory,thus db name must be changed in application.properties file.
       If Posgres is not what you like, you can configure a new data source, by adding the right dependencies in pom.xml and configure it in application.properties

** App functionality, how it works, and some additional information **
      The main controller create a local file on the system. By default, it is saved under C\\test\. This directory is created automatically. IF the location where file is saved needs to be changed, it can be done in CsvFileService.java, from package ro.yonder.weatherapp.Weather.App.Demo.domain.services line 20. Also name of the file can be changed accordingly. By default, name of the file is weather.csv . The app will generate a CSV file after a success call to weather API, and return a JSON to the end user, having the following output:

      [
    {
        "name": "Arad",
        "temperature": "22.00 ℃",
        "wind": "11.00 km/h"
    },
    {
        "name": "Bucuresti",
        "temperature": "31.67 ℃",
        "wind": "7.00 km/h"
    },
    {
        "name": "Oradea",
        "temperature": "19.34 ℃",
        "wind": "9.67 km/h"
    }
  ]
      
      
      This app calls https://goweather.herokuapp.com/weather/{cityName} API, where cityName is a name of any city. For example https://goweather.herokuapp.com/weather/Bucuresti returns the following input in JSON format:

       {"temperature":"+35 °C","wind":"4 km/h","description":"Sunny","forecast":[{"day":"1","temperature":"29 °C","wind":"8 km/h"},{"day":"2","temperature":"31 °C","wind":"8 km/h"},{"day":"3","temperature":" °C","wind":"10 km/h"}]} .
 
       WeatherRestController handle the requests made by the user by calling the following resource: http://localhost:8080/api/weather?city=Bucuresti,Arad. It can handle multiple city names will return a JSON input like the one above for each city name. NOTES: from time to time, weather API is not available for a short period of time (5-10 mins). This app handle this issue, by returning a proper message when the service in not available. In addition of this, it also handle the case where we have a city name that does not exists. This will also return a proper message to the user. When we encounter this issue, we delete all records saved until that point, to avoid having records in database but no CSV file generated.

       Old records will be erased when the API is called again, and a new set of records and a new file is generated. 
