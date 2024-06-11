<template>
  <div>
    <div style="width: 80%; margin: 20px auto; text-align: center;">
      <h1>搜尋結果：</h1>
    </div>

    <div style="width: 80%; margin: 0 auto; text-align: center;">
      <div class="input-group mb-3">
        <input type="text" class="form-control custom-input" aria-label="Recipient's username" aria-describedby="button-addon2" v-model="searchKeyword" placeholder="輸入搜尋關鍵字" @keyup.enter="search" />
        <button class="btn btn-outline-secondary custom-button" type="button" id="button-addon2" @click="search">搜索</button>
      </div>
      <div class="btn-group" role="group" aria-label="基本輪廓示例" style="width: 100%;">
        <button type="button" class="btn btn-outline-primary" @click="filterResults('/search')">全部</button>
        <button type="button" class="btn btn-outline-primary" @click="filterResults('/search/traveler')">旅者</button>
        <button type="button" class="btn btn-outline-primary" @click="filterResults('/search/vender')">商家</button>
      </div>
    </div>

    <div style="width: 75%; margin: 0 auto;">
      <div v-if="searchResults.length > 0">
        <p>共 {{ searchResults.length }} 筆結果</p>
        <ul class="search-results">
          <li class="card mb-3" style="max-width: 90%; text-align: center; padding: 40px; margin: 0 auto;" v-for="(result, index) in searchResults" :key="index">
            <router-link :to="result.venderId ? '/vender/' + result.venderId : '/blog/' + result.travelerId">
              <div class="row g-0">
                <div class="col-md-4">
                  <template v-if="result.venderId">
                    <!-- 显示商家图像 -->
                
                    <svg class="bd-placeholder-img" width="200" height="200" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false">
                      <title>Placeholder</title>
                      <rect width="100%" height="100%" />
                      <image :xlink:href="getVenderPhoto(result.venderId)" width="100%" height="100%" />
                    </svg>
                  </template>
                  <template v-else-if="result.travelerId && getTravelerPhoto(result.travelerId) !== ''">
                    <!-- 显示旅者图像 -->
       
                    <div class="circular-container" style="width: 250px; height: 250px;">
                      <img :src="getTravelerPhoto(result.travelerId)" alt="Profile Image" style="width: 100%; height: 100%; object-fit: cover;">
                    </div>
                  </template>
                </div>
                <div class="col-md-8">
    <h5 class="card-title">{{ result.venderId ? result.venderconame : result.travelerName }}</h5>
    <template v-if="result.venderId">
      <p class="card-text">{{ result.vendertel }}</p>
    </template>
    <template v-else>
      <p class="card-text">
  {{ result.travelerAcct || '' }}<br>
  {{ result.mobile || '' }}<br>
  {{ result.sexAbbr || '' }}
</p>
    </template>
  </div>
              </div>
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref } from 'vue';
import TravelerList from '../views/TravelerList.vue';
import VenderList from '../views/VenderList.vue';

const searchKeyword = ref('');  
const searchResults = ref([]);
const filterType = ref(''); // 改为字符串
const venders = ref([]); // 确保包含正确的数据
const Travelers = ref([]); // 确保包含正确的数据

const search = async () => {
  if (searchKeyword.value) { // 检查搜索关键字是否为空
    try {
      if (filterType.value == '') {
        filterType.value = '/search';
      }
      const response = await axios.get(`http://localhost:8084/easystep${filterType.value}?q=${searchKeyword.value}`);
      searchResults.value = response.data;
    } catch (error) {
      console.error('Error searching:', error);
    }
  } else {
    // 清空搜索结果
    searchResults.value = [];
  }
};

const filterResults = (filter) => {
  filterType.value = filter;
  search();
};

const getVenderPhoto = (venderId) => {
  const vender = venders.value.find(v => v.venderId === venderId);
  return vender ? vender.showphoto : '';
};

const getTravelerPhoto = (travelerId) => {
  const traveler = Travelers.value.find(t => t.travelerid === travelerId);
  return traveler ? traveler.photoURL : '';
};
</script>

<style scoped>
.search-results {
  list-style: none;
}
</style>