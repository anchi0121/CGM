<script setup>
import changebotton from '../components/changebotton.vue';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

const Album = ref('');
const trdatas = ref('');
const travelername = ref('');
const user = ref('');
const route = useRoute();
const path = user.value = route.params.travelerId;

// 取得每個相簿的第一張相片的 URL
const albumCoverUrls = ref([]);
//取得文章
const Articles = ref([]);


//取得session
const travelerid = sessionStorage.getItem('travelerid');
const login = sessionStorage.getItem('traveler');


//取得旅者資料
const trdownload = async () => {
	const URL = `${import.meta.env.VITE_API_easystep}/blog/${path}`;
	try {
		const response = await axios.get(URL, {
			withCredentials: true,
			cache: false,
		});
		trdatas.value = response.data;
		console.log(response.data.infoDTo);
		travelername.value = response.data.username;
	} catch (error) {

	}
};

//下載文章
const download = async () => {
	const URL = `${import.meta.env.VITE_API_easystep}/article/TopArticle/${path}`;
	try {
		const response = await axios.get(URL, {
			withCredentials: true,
			cache: false,
		});
		Articles.value = response.data;
		console.log(response.data);
	} catch (error) {
		console.log(error);
	}
};

//計算文章長度給省略號
const truncateText = (text, maxLength) => {
	if (text.length > maxLength) {
		return text.slice(0, maxLength) + '...';
	}
	return text;
};


// 取得所有該旅者的相簿和相片
const phdownload = async () => {
	const URL = `${import.meta.env.VITE_API_easystep}/photo/${path}`;
	try {
		const response = await axios.get(URL, {
			withCredentials: true,
			cache: false,
		});

		// 保存相簿信息
		Album.value = response.data;

		// 取得每個相簿的第一張相片的 URL
		albumCoverUrls.value = await Promise.all(
			response.data.map(async (album) => {
				// 獲取相簿的相片
				const photosResponse = await axios.get(
					`${import.meta.env.VITE_API_easystep}/photo/showphoto?albumId=${album.albumId}`,
					{ withCredentials: true, cache: false }
				);

				// 如果有相片，返回第一張相片的 URL，否則返回一個預設 URL
				if (photosResponse.data.length > 0) {
					return photosResponse.data[0].photoURL;
				} else {
					return 'https://res.cloudinary.com/dciwmbvcl/image/upload/v1698651546/hn2wmztbc8lxvwrzf24k.jpg'; // 將這個 URL 替換為你的預設圖片 URL
				}
			})
		);

	} catch (error) {
		console.error(error);
	}
};

onMounted(() => {
	trdownload();
	download();
	phdownload();
});
</script>

<template>
	<div class="container" style="background-image: url('./img/about.png');
">
		<changebotton></changebotton>
		<div class="container text-center">
			<div class="row ">
				<div class="col">
					<div class="card mt-5 mb-5 mx-auto" style="max-width: 800px; ">
						<div class="tab-content" id="nav-tabContent">
							<div class="tab-pane fade show active" id="nav-home" role="tabpanel"
								aria-labelledby="nav-home-tab" tabindex="0">
								<div class="card-body">
									<div class="card mb-3 mx-auto border border-white " style="max-width: 320px; ">
										<div class="card-body d-flex justify-content-center">
											<div class="circular-container border border-5">
												<img :src="trdatas.photoURL" class="card-img-top " id="photo" alt="...">
											</div>
										</div>
										<div class="card-body">
											<h1 class="card-title" style="font-family: 'Caveat', cursive;">{{
												trdatas.username
											}} </h1>
											<ul>
												<li>{{ trdatas.email }}</li>
												<li>{{ trdatas.mobile }}</li>
												<li>Sex: {{ trdatas.sexabbr }}</li>
											</ul>
										</div>
									</div>
									<div class="modal-footer">
										<RouterLink to="/Traveler/TravelerUpdate" v-if="travelerid === path && login">
											<button type="button" class="btn btn-primary">編輯個人資料</button>
										</RouterLink>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col">
					<div style="height:600px;width:400px;overflow-y: scroll; overflow-x: hidden;">
						<H2>最新文章</H2>
						<div class="album py-5 bg-body-tertiary">
							<div class="container">
								<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4 container">
									<div class="card " style="width: 18rem; margin-left: 10px;" v-for="Article in Articles"
										:key="Article.ArticleId">
										<div class="card-body">
											<h5 class="card-title">{{ Article.title }}</h5>
											<p class="card-text">{{ truncateText(Article.text, 30) }}</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col">
					<div style="height:600px;width:400px;overflow-y: scroll; overflow-x: hidden;">
						<h2>近期相片</h2>
						<div class="album py-5 bg-body-tertiary">
							<div class="container">
								<div class="col" v-for="(album, index) in Album" :key="album.albumId">
									<div class="card shadow-sm">
										<div class="card-img-top"
											style="width: 100%; height: 225px; background-color: #55595c;"
											v-if="!albumCoverUrls[index]">
											<!-- <text x="50%" y="50%" fill="#eceeef" dy=".3em">{{ album.albumName }}</text> -->
										</div>
										<img :src="albumCoverUrls[index]" id="photo" class="card-img-top" alt="album cover"
											width="100%" height="225" v-else>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Caveat&display=swap');



ul {
	list-style-type: none;
}

li {
	font-family: 'Caveat', cursive;
	font-size: 30px;
}

.circular-container {
	width: 230px;
	/* 調整寬度和高度以設置圓形的大小 */
	height: 230px;
	border-radius: 50%;
	/* 將邊界半徑設置為50%以創建圓形 */
	overflow: hidden;
	/* 隱藏超出圓形容器的內容 */
}

#photo {
	width: 100%;
	height: 100%;
	object-fit: cover;
}
</style>
