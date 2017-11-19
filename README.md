# arch-demo
Proof of concept with google's new arch objects (Viewmodel, LiveData, Room)

This app will fetch weather data from hard-coded cities every 30 minutes using a JobScheduler
and put it inside a room database. Everytime the user opens the app, the ListActivity will have
received the latest weather data from the database.

TO-DO:
- Check Diffutil utility for recyclerview adapters so that i don't recreate an adapter everytime the data changes
- Let user add cities of his choice instead of hardcoding it
- On click of an item, show more weather details (for example, 5-day forecast)