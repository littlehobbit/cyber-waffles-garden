<script>
     /* eslint-disable no-undef */
     import { computed, ref, onMounted, onUnmounted, watch } from 'vue'
     import { useGeolocation } from './useGeolocation'
     import { Loader } from '@googlemaps/js-api-loader'
     const GOOGLE_MAPS_API_KEY = 'AIzaSyAO68ev8VYxzNdOfYPbZsJyQQZJYlG2Ab0'

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
                    map.value = new google.maps.Map(mapDiv.value, {
                         center: currPos.value,
                         zoom: 11,
                    })
                    clickListener = map.value.addListener(
                            'click',
                            ({ latLng: { lat, lng } }) =>
                                    (otherPos.value = { lat: lat(), lng: lng() })
                    )
               })
               onUnmounted(async () => {
                    if (clickListener) clickListener.remove()
               })

              let marker;
              watch([map, otherPos], () => {
                  if (marker) marker.setMap(null)
                  if (map.value && otherPos.value != null)
                      marker = new google.maps.Marker(
                          {position:{lat:otherPos.value.lat,lng:otherPos.value.lng}, map:map.value}
                      );
              })

               return { currPos, otherPos, mapDiv }
          },

         data(){
              return {
                  markers: []
              }

         },
         methods: {
             drawMarkers(){
                 this.markers = [{
                     position: Marker
                 }]

                 if (marker) marker.setMap(null)
                 if (map.value && otherPos.value != null)
                     marker = new google.maps.Marker(
                         {position:Marker, map:map.value}
                     );
             }
         }
     }
</script>

<template>
     <div class="d-flex text-center" style="height: 20vh">
         <button @click="drawMarkers">Draw Markers</button>
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
     <div ref="mapDiv" style="width: 100%; height: 80vh" />
</template>