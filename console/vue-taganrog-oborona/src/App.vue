<script>
    /* eslint-disable no-undef */
    import axios from 'axios'
    import { computed, ref, onMounted, onUnmounted, watch } from 'vue'
    import { useGeolocation } from './useGeolocation'
    import { Loader } from '@googlemaps/js-api-loader'
    const GOOGLE_MAPS_API_KEY = 'AIzaSyAO68ev8VYxzNdOfYPbZsJyQQZJYlG2Ab0'
    const Marker = {lat:0, lng: 0}
    let mmarkers = []
    let Map = 0
    let Information = []

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
                Array:{date:'',
                    time:'',
                    location:'',
                    nameOfEvent:''
                },
                tuta: null

            }

        },

        methods: {
            drawMarkers(){

                let marker
                if (Marker.lat !== null && Marker.lng !== null)
                    marker = new google.maps.Marker(
                        {
                            position: Marker,
                            title: 'Hello man 123!',
                            draggable: false
                        }
                    );

                const infowindow = new google.maps.InfoWindow({
                    content: '<div id="content">' +
                        '<div id="siteNotice">' +
                        "</div>" +
                        '<h1 id="firstHeading" class="firstHeading">'+Array.nameOfEvent+'</h1>' +
                        '<div id="bodyContent">' +
                        '<p>'+Array.location+'</p>' +
                        '<p>'+Array.date + ',\n' + Array.time + '</p>' +
                        "</div>" +
                        "</div>"
                });

                marker.addListener("click", () => {
                    infowindow.open(Map, marker);
                });
                let Prikol = new Array({
                    date:Array.date,
                    time:Array.time,
                    location:Array.location,
                    nameOfEvent:Array.nameOfEvent
                })
                Information.push(Prikol)
                for (let i = 0; i < Information.length; i++) {
                    console.log(Information[i].date,Information[i].time,Information[i].location,Information[i].nameOfEvent)
                }
                console.log(Information)
                marker.setMap(Map)
                mmarkers.push(marker)
            },

            ClearMarkers(){

                for (let i = 0; i < mmarkers.length; i++) {
                    mmarkers[i].setMap(null)
                }
                Information = []
                mmarkers = []
            },

            Clear_last(){
                if(mmarkers.length !== 0) {
                    mmarkers[mmarkers.length - 1].setMap(null)
                    mmarkers.pop()
                    Information.pop()
                }
            },

            AddInfo(){
                axios.get('https://192.168.43.222:3000/bonuses/all').then(response => (this.tuta = response)).catch(error => console.log(error));

                for(let i=0; i<this.mmarkers;i++)
                {
                    console.log(this.mmarkers[i])
                }
                 console.log(this.tuta)
            }
        }
    }
</script>

<template>

    <div>
        <button @click="drawMarkers">Draw Markers</button>
        <button @click="ClearMarkers">Delete Markers</button>
        <button @click="Clear_last">Clear last</button>
    </div>

    <div>
        <input id="in1" type="text" v-model="Array.date" placeholder="Введите дату">
        <input id="in2" type="text" v-model="Array.time" placeholder="Введите время">
        <input id="in3"  type="text" v-model="Array.location" placeholder="Введите место проведение">
        <input id="in4"  type="text" v-model="Array.nameOfEvent" placeholder="Введите событие">

        <div>
            <button @click="AddInfo">Add Injormation</button>
        </div>
        {{tuta}}
    </div>
    <div id="map" style="width: 70%; height: 80vh" />

</template>
