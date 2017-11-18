package com.tillylabs.okhttpdemo.serviceLocator

/**
 * Created by steven on 2017-11-18.
 */
class ServiceImpl /*: Service*/{

    /*@SuppressLint("StaticFieldLeak")
    override fun fetchInformation(): LiveData<List<WeatherData>>{
        var data: MutableLiveData<List<WeatherData>>? = null
        object : AsyncTask<Void, Void, List<WeatherData>>() {

            override fun doInBackground(vararg voids: Void): List<WeatherData> {
                val list = ArrayList<WeatherData?>()
                try {
                    list.add(WeatherData.getWeatherData("Montreal"))
                    list.add(WeatherData.getWeatherData("Boisbriand"))
                    list.add(WeatherData.getWeatherData("Sainte-Thérèse"))
                    list.add(WeatherData.getWeatherData("Laval"))

                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return list.filterNotNull()
            }
            override fun onPostExecute(list: List<WeatherData>) {
                data = MutableLiveData()
                data!!.value = list
            }
        }.execute()
        return data
    }*/
}