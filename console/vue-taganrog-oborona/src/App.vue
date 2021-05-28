<script>
     /* eslint-disable no-undef */
     import { computed, ref, onMounted, onUnmounted, watch } from 'vue'
     import { useGeolocation } from './useGeolocation'
     import { Loader } from '@googlemaps/js-api-loader'
     const GOOGLE_MAPS_API_KEY = 'AIzaSyAO68ev8VYxzNdOfYPbZsJyQQZJYlG2Ab0'
     const Marker = {lat:0, lng: 0}
     let mmarkers = []
     let Map = 0
     export default {
          name: 'App',
          setup() {
               const { coords } = useGeolocation()
               const currPos = computed(() => ({
                    lat: 47.22,
                    lng: 38.76
               }))

               const otherPos = ref(null)
               const loader = new Loader({ apiKey: GOOGLE_MAPS_API_KEY })
               const mapDiv = ref(null)
               let map = ref(null)
               let clickListener = null

               onMounted(async () => {
                    await loader.load()
                    map = new google.maps.Map(document.getElementById("map"), {
                         center: currPos.value,
                         zoom: 11,
                    })
                    clickListener = map.addListener(
                            'click',
                            ({ latLng: { lat, lng } }) =>
                                    (otherPos.value = { lat: lat(), lng: lng() },
                                    Marker.lat = otherPos.value.lat,
                                            Marker.lng = otherPos.value.lng,
                                            Map = map
                                    )
                    )
               })
               onUnmounted(async () => {
                    if (clickListener) clickListener.remove()
               })

               return { currPos, otherPos, mapDiv }
          },

         data(){
              return {

              }

         },
         methods: {
             drawMarkers(){

                 console.log(this.markers)

                 let marker
                 if (Marker.lat !== null && Marker.lng !== null)
                     marker = new google.maps.Marker(
                         {
                             position: Marker,
                             title: 'Hello man 123!'
                         }
                     );
                 marker.setMap(Map)
                 mmarkers.push(marker)
                 for (let i = 0; i < mmarkers.length; i++) {
                     console.log(mmarkers[i].map)
                 }

             },

             ClearMarkers(){

                 for (let i = 0; i < mmarkers.length; i++) {
                     mmarkers[i].setMap(null)
                 }
                 mmarkers = []
             },

             Clear_last(){
                 if(mmarkers.length !== 0) {
                     mmarkers[mmarkers.length - 1].setMap(null)
                     mmarkers.pop()
                 }
             }
         }
     }
</script>

<template>
     <div class="d-flex text-center" style="height: 20vh">
         <button @click="drawMarkers">Draw Markers</button>
         <button @click="ClearMarkers">Delete Markers</button>
         <button @click="Clear_last">Clear last</button>
          <div class="m-auto">
               <h4>Your Position</h4>
               Latitude: {{ currPos.lat.toFixed(2) }}, Longitude:
               {{ currPos.lng.toFixed(2) }}
          </div>
          <div class="m-auto">
               <h4>Clicked Position</h4>
               <span v-if="otherPos">
        Latitude: {{ otherPos.lat.toFixed(2) }}, Longitude:
        {{ otherPos.lng.toFixed(2) }}
      </span>
               <span v-else>Click the map to select a position</span>
          </div>
     </div>
     <div id="map" style="width: 100%; height: 80vh" />
</template>
