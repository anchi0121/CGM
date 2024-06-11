<template>
	<div>
		<nav class="navbar navbar-expand-lg navbar-light ">
			<div class="container-fluid">
				<RouterLink to="/"><img src="../assets/about_version.png" alt="" id="logo"></RouterLink>
				<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
					data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav ml-auto">
						<li class="nav-itemp-2 ">
							<RouterLink class="nav-link mx-auto" to="/venderList">尋找廠商</RouterLink>
						</li>
						<li class="nav-itemp-2 ">
							<RouterLink class="nav-link mx-auto" to="/traveler/list">尋找幫手</RouterLink>
						</li>
						<li class="nav-itemp-2 ">
							<RouterLink class="nav-link mx-auto" to="/FAQ">打工須知</RouterLink>
						</li>
					</ul>

				</div>
				<div class="mt-2 mt-md-0">
					<div class="collapse navbar-collapse" id="navbarSupportedContent">
						<ul class="navbar-nav ml-auto">
							<li class="nav-itemp-2 " v-if="logoutVisible && trVisible">
								<RouterLink class="nav-link mx-auto" to="/tmessages">聊聊</RouterLink>
							</li>
							<li class="nav-item dropdown" v-if="logoutVisible && veVisible">
								<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
									aria-expanded="false">
									聊聊
								</a>
								<ul class="dropdown-menu">
									<li>
										<RouterLink to="/vmessages" class="dropdown-item">更多聊聊~</RouterLink>
									</li>
									<li v-for="{ travelerId, travelerName } in talkMans">
										<button class="button-like nav-item nav-link link-body-emphasis"
											@click="clickHandler(travelerId)">
											{{ travelerName }}
										</button>
									</li>
								</ul>
							</li>
							<li class="nav-item dropdown" v-if="logoutVisible">
								<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
									aria-expanded="false">
									用戶專區
								</a>
								<ul class="dropdown-menu">
									<li v-if="trVisible">
										<RouterLink to="/Traveler/TravelerUpdate" class="dropdown-item">小幫手</RouterLink>
									</li>
									<li v-if="trVisible">
										<RouterLink to="/Traveler/LikeVender" class="dropdown-item">收藏廠商</RouterLink>
									</li>
									<li v-if="veVisible">
										<RouterLink to="/Vender/VenderUpdate" class="dropdown-item">廠商</RouterLink>
									</li>
									<li v-if="trVisible">
										<RouterLink :to="'/blog/' + travelerid" class="dropdown-item">Blog</RouterLink>
									</li>

									<li v-if="veVisible">
										<RouterLink to="/addVenderInformation" class="dropdown-item">工作資訊</RouterLink>
									</li>
								</ul>
							</li>

							<li class="nav-item dropdown ml-auto" v-if="logVisible">
								<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
									aria-expanded="false">
									登入
								</a>
								<ul class="dropdown-menu">
									<li>
										<RouterLink to="/Traveler/TravelerLog" class="dropdown-item">小幫手</RouterLink>
									</li>
									<li>
										<RouterLink to="/Vender/VenderLog" class="dropdown-item">廠商</RouterLink>
									</li>
								</ul>
							</li>
							<li class="nav-item dropdown ml-auto" v-if="logVisible">
								<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
									aria-expanded="false">
									註冊
								</a>
								<ul class="dropdown-menu">
									<li>
										<RouterLink to="/Traveler/TravelerRegister" class="dropdown-item">小幫手</RouterLink>
									</li>
									<li>
										<RouterLink to="/Vender/VenderRegister" class="dropdown-item">廠商</RouterLink>
									</li>
								</ul>
							</li>
							<li class="nav-item ml-auto">
								<button v-if="logoutVisible" class="btn btn-light" @click="logout"
									v-show="logoutVisible">登出</button>
							</li>
						</ul>
					</div>
				</div>
				
				<form class="d-flex">
					<input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
					<RouterLink class="nav-link mx-auto" to="/search">
					<button class="btn btn-outline-success" type="submit">Search</button>
				</RouterLink>
				</form>
			
			</div>
		</nav>

	</div>
</template>
    
<script setup >


import axios from 'axios';
import { ref, onMounted, } from 'vue'


//取得session
const travelerid = sessionStorage.getItem('travelerid');

const logout = async () => {
	const URL = `http://localhost:8084/easystep/Vender/venderlogout`
	const response = await axios.get(URL, {
		withCredentials: true,
	})
	if (response.data == 'ok') {
		console.log(response.data);
		setTimeout("location.href='/'", 1000);
		sessionStorage.clear();
		sessionStorage.removeItem('travelerid');
	}
}

const veVisible = ref(true);
const trVisible = ref(true);
const logoutVisible = ref(false);
const logVisible = ref(true);

const vIsLoggedIn = sessionStorage.getItem('vender');
const tIsLoggedIn = sessionStorage.getItem('traveler');

const logout2 = () => {
	if (vIsLoggedIn) {
		trVisible.value = false
		logoutVisible.value = true
		logVisible.value = false
		console.log(vIsLoggedIn)

	} else if (tIsLoggedIn) {
		veVisible.value = false
		logoutVisible.value = true
		logVisible.value = false
		console.log(tIsLoggedIn)
	}

}

const talkMans = ref([])
const emit = defineEmits(["childVenderClick"])
const clickHandler = travelerId => {
	emit("childVenderClick", travelerId)
}
// 載入聊過天的 旅者 跟查看登入狀態
const openTalk = async () => {
	const URL = `${import.meta.env.VITE_API_easystep}/vender/checklogin`
	const response = await axios.get(URL, { withCredentials: true })
	if (response.data.success) {

		const API = `${import.meta.env.VITE_API_easystep}/chatroom/findtras`
		const responses = await axios.get(API, { withCredentials: true })
		talkMans.value = responses.data
	}
}


onMounted(() => {
	logout2()
	openTalk()
})




</script>
    
<style scoped>
#logo {
	width: 50%;
	height: 50%;
	object-fit: cover;
}
</style>