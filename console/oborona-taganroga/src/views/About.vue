<script>
import App from '../App'
  /* eslint-disable no-undef */
  import axios from 'axios'
  import { computed, ref, onMounted, onUnmounted, watch } from 'vue'
  import { useGeolocation } from '../useGeolocation'
  import { Loader } from '@googlemaps/js-api-loader'
  const GOOGLE_MAPS_API_KEY = 'AIzaSyAO68ev8VYxzNdOfYPbZsJyQQZJYlG2Ab0'
  const Marker = {lat:0, lng: 0}
  let mmarkers
  let Map = 0
  let Information
  let cafe
  let last_click

  function addMarkersOnMap(response){
    console.log(response);
  }

  function createEvent(Array, accessToken, refreshToken, marker){
    var xhr = new XMLHttpRequest()
    xhr.open("POST", "http://192.168.43.143:3000/admin/event");
    xhr.setRequestHeader("Authorization", "Bearer " + accessToken);
    xhr.setRequestHeader("Content-Type", "application/json");
    var data = {
      name:Array.nameOfEvent,
      description:"",
      address:Array.location,
      datetime: Array.date + " " + Array.time+":00",
      place:{
        y:Marker.lng,
        x:Marker.lat
      },
      type:Array.type,
      group:Array.group,
      description:Array.description
    }
    xhr.send(JSON.stringify(data));
    xhr.onreadystatechange = ()=>{
      if(xhr.readyState ===4){
        console.log(xhr.status)
        if(xhr.status === 201){
          console.log("Done");
          marker.id = xhr.response;
          marker.type ="event";
        }
        if(xhr.status === 403){
          refreshAccess(refreshToken);
          createEvent(Array);
        }
      }
    }
  }

  function createBonus(Array, accessToken, refreshToken, marker){
    var xhr = new XMLHttpRequest()
    xhr.open("POST", "http://192.168.43.143:3000/admin/bonus");
    xhr.setRequestHeader("Authorization", "Bearer " + accessToken);
    xhr.setRequestHeader("Content-Type", "application/json");
    var data = {
      name:Array.nameOfEvent,
      description:"",
      address:Array.location,
      place:{
        x:Marker.lng,
        y:Marker.lat
      }
    }
    xhr.send(JSON.stringify(data));
    xhr.onreadystatechange = ()=>{
      if(xhr.readyState ===4){
        console.log(xhr.status)
        if(xhr.status === 201){
          console.log("Done");
          marker.id = xhr.response;
          marker.type ="bonus";
        }
        if(xhr.status === 403){
          refreshAccess(refreshToken);
          createBonus(Array);
        }
      }
    }
  }

  function deleteEvent(accessToken, refreshToken, marker){
    var xhr = new XMLHttpRequest()
    xhr.open("DELETE", "http://192.168.43.143:3000/admin/event");
    xhr.setRequestHeader("Authorization", "Bearer " + accessToken);
    xhr.setRequestHeader("Content-Type", "application/json");
    console.log(accessToken)
    var data = {
      id:marker.id
    }
    xhr.send(JSON.stringify(data));
    console.log(data);
    xhr.onreadystatechange = ()=>{
      if(xhr.readyState ===4){
        console.log(xhr.status)
        if(xhr.status === 202){
          console.log("Done");
          marker.id = null;
          marker.type = null;
        }
        if(xhr.status === 403){
          refreshAccess(refreshToken);
          deleteEvent();
        }
      }
    }
  }

  function deleteBonus(accessToken, refreshToken, marker){
    var xhr = new XMLHttpRequest()
    xhr.open("DELETE", "http://192.168.43.143:3000/admin/event");
    xhr.setRequestHeader("Authorization", "Bearer " + accessToken);
    xhr.setRequestHeader("Content-Type", "application/json");
    var data = {
      id:marker.id
    }
    xhr.send(JSON.stringify(data));
    xhr.onreadystatechange = ()=>{
      if(xhr.readyState ===4){
        console.log(xhr.status)
        if(xhr.status === 202){
          console.log("Done");
          marker.id = null;
          marker.type = null;
        }
        if(xhr.status === 403){
          refreshAccess(refreshToken);
          deleteBonus();
        }
      }
    }
  }

  function refreshAccess(refreshToken){
    var xhr = new XMLHttpRequest()
    xhr.open("GET", "http://192.168.43.143:3000/refresh");
    xhr.setRequestHeader("Content-Type", "aplication/json");
    var data = {
      token:refreshToken
    }
    xhr.onreadystatechange = ()=>{
      if(xhr.readyState ===4){
        if(xhr.status === 200){
          this.accessToken = JSON.parse(xhr.response).accesstoken;
        }
      }
    }
    xhr.send(data);
  }

  class userMarker{
    id = 0;
    type = "";
    marker = null;
    parent = this;
  }

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
          nameOfEvent:'',
          description:'',
          type:'',
          group:''
        },
        tuta: null,
        cafe: [],
        accessToken: 0,
        refreshToken: 0

      }

    },

    methods: {
      drawMarkers(){
        let marker
        if (Marker.lat !== null && Marker.lng !== null)
          marker = new userMarker();
          marker.marker = new google.maps.Marker(
                  {
                    position: Marker,
                    title: '!!!',
                    draggable: false,
                    clickable: true 
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

        marker.marker.addListener("click", () => {
          infowindow.open(Map, marker.marker);
          last_click = marker;
        });
        try{
          createEvent(Array, this.accessToken, this.refreshToken, marker);
          marker.marker.setMap(Map);
        }
        catch{}
      },

      AddInfo(){
          const requestURL = 'http://192.168.43.143:3000/events/all'
          const xhr = new XMLHttpRequest()
          xhr.open('GET', requestURL, true)
          xhr.responseType = 'json'
          xhr.send();
          xhr.onreadystatechange = () =>{
            if(xhr.readyState === 4){
              if(xhr.status === 200){
                console.log(xhr.response.res);
                var marks = xhr.response.res;
                  marks.forEach(element => {
                  let mark = new userMarker();
                  Marker.lat = element.EVENT_PLACE.y;
                  Marker.lng = element.EVENT_PLACE.x;
                  mark.marker = google.maps.Marker({
                    title: '!!!',
                    draggable: false,
                    position:Marker,
                    clickable: true
                  })
                  mark.marker.clickListener = ()=>{
                    last_click = mark;
                  }
                  mark.marker.setMap(Map);
                  mark.id = element.ID;
                  mark.type = "event";
                });
              }
            }
          }
          // axios.get(requestURL)
          //   .then(response => (console.log(response)));
     },

     AddCafe(){
        let marker
        if (Marker.lat !== null && Marker.lng !== null)
          marker = new userMarker();
          marker.marker = new google.maps.Marker(
                  {
                    position: Marker,
                    title: '!!!',
                    draggable: false,
                    clickable: true 
                  }
          );
          marker.marker.addListener('click', () => {
            last_click = marker
          }

        )
        try{
          createBonus(Array, this.accessToken, this.refreshToken, marker);
          marker.marker.setMap(Map);
        }
        catch{}
     },

        Delete_marker(){
        try{
          console.log(last_click);
          console.log(this.accessToken)
          console.log(this.refreshToken);
          if(last_click.type == "bonus") deleteBonus(this.accessToken, this.refreshToken, last_click);
          else deleteEvent(this.accessToken, this.refreshToken, last_click);
          if(last_click !== null) {
            last_click.marker.setMap(null)
            last_click=null
          }
        }
        catch{}
      },

    },
    created(){
      this.refreshToken = sessionStorage.getItem("refreshToken");
      console.log(this.refreshToken);
      this.accessToken = sessionStorage.getItem("accessToken");
      console.log(this.accessToken);
    }
  }
</script>

<template>
  <div>
    <button @click="drawMarkers">Draw Markers</button>
    <button @click="AddCafe">Draw Cafe Horiba</button>
    <button @click="Delete_marker">Delete marker</button>
  </div>

  <div>
    <input id="in1" type="date" v-model="Array.date" placeholder="Введите дату">
    <input id="in2" type="time" v-model="Array.time" placeholder="Введите время">
    <input id="in3"  type="text" v-model="Array.location" placeholder="Введите место проведение">
    <input id="in4"  type="text" v-model="Array.nameOfEvent" placeholder="Введите событие">
    <input id="in5" type="text" v-model="Array.type" placeholder="Введите тип">
    <input id="in6" type="text" v-model="Array.group" placeholder="Введите группу">
    <input id="in7"  type="text" v-model="Array.description" placeholder="Введите описание">

    <div>
      <button @click="AddInfo">Add Injormation</button>
    </div>
  </div>


  <div id="map" style="width: 100%; height: 70vh" />

</template>