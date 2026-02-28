package com.example.sportapp.models

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

// Einfache Datenklasse für unsere Koordinaten


class MatchingApiClient(private val apiKey: String) {

    private val client = OkHttpClient()

    // Hier ist die Variable, in der die Koordinaten gespeichert werden.
    // Sie ist von außen lesbar, kann aber nur intern verändert werden (private set).
    var matchedCoordinates: List<Coordinate> = emptyList()
        private set

    /**
     * Ruft die Matching API auf und speichert die angepassten Routen-Koordinaten in der Variable.
     * @param rawPath Die ursprünglichen, ungenauen GPS-Punkte.
     * @return true wenn erfolgreich, false wenn ein Fehler aufgetreten ist.
     */
    suspend fun fetchMatching(rawPath: List<Coordinate>): Boolean {
        return withContext(Dispatchers.IO) {
            // 1. Koordinaten für die URL formatieren (Format: lon,lat;lon,lat)
            val coordinatesString = rawPath.joinToString(";") { "${it.longitude},${it.latitude}" }

            // Beispiel-URL für die Mapbox Map Matching API (Driving Profile)
            val url = "https://api.mapbox.com/matching/v5/mapbox/driving/$coordinatesString?geometries=geojson&access_token=$apiKey"

            val request = Request.Builder()
                .url(url)
                .build()

            try {
                // 2. Request ausführen
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        println("Fehler bei der API-Anfrage: ${response.code}")
                        return@withContext false
                    }

                    val responseBody = response.body?.string() ?: return@withContext false

                    // 3. JSON-Antwort parsen
                    val jsonObject = JSONObject(responseBody)
                    val matchings = jsonObject.optJSONArray("matchings")

                    if (matchings != null && matchings.length() > 0) {
                        val geometry = matchings.getJSONObject(0).getJSONObject("geometry")
                        val coordsArray = geometry.getJSONArray("coordinates")

                        val parsedCoordinates = mutableListOf<Coordinate>()

                        // Über das JSON-Array iterieren und Koordinaten-Objekte erstellen
                        for (i in 0 until coordsArray.length()) {
                            val coord = coordsArray.getJSONArray(i)
                            // GeoJSON Format ist typischerweise [Longitude, Latitude]
                            parsedCoordinates.add(Coordinate(coord.getDouble(0), coord.getDouble(1)))
                        }

                        // 4. In der Klassenvariable speichern!
                        matchedCoordinates = parsedCoordinates
                        return@withContext true
                    }
                    return@withContext false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext false
            }
        }
    }
}