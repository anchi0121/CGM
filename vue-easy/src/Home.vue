<template>
  <h1>首頁</h1>
<div th:text="${session.loginuser}?'您好'+${session.loginuser.username}"></div>      
<div id="loginStatus"></div>
<!-- <div >{{datas}}</div>
<div >{{trdatas}}</div>

<div >{{Dto}}</div> -->
<div>{{travelername}}{{vendername}}歡迎登入Easystep</div>

</template>
    
<script setup>

    import { ref ,onMounted,onBeforeMount,onUpdated} from 'vue'
    import axios from 'axios';
    // const  datas = ref(''); // 用于显示消息的变量
    // const  trdatas = ref('');
    const  vendername = ref('');
    const  travelername = ref('');
    // const  Dto = ref('');

   

    const download=async () => {
      const URL=`http://localhost:8084/easystep/Vender/getlogin`
      const response=await axios.get(URL,{
        withCredentials:true,
        cache: false
      })
      // datas.value= response.data;
      console.log(response.data.infoDTo)
   
      vendername.value=response.data.username;
    }

    const trdownload=async () => {
      const URL=`http://localhost:8084/easystep/Traveler/getlogin`
      const response=await axios.get(URL,{
        withCredentials:true,
        cache: false
      })
      // trdatas.value= response.data;
      console.log(response.data.infoDTo)
   
      travelername.value=response.data.username;
    }

    onBeforeMount(()=>{
      download()
      trdownload()
    })
  
  


</script>
<style scoped>
    
</style>