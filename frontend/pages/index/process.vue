<template>
	<view style="padding: 1%;">
		<view>
			请选择要校对的文本
		</view>
		<!-- 图片预览 + 文字提取 -->

		<view class="wrap">
			<u-scroll-list :indicator="true" indicatorColor="#fff0f0" indicatorActiveColor="#f56c6c">
				<view v-for="(item, index) in picList" :key="index" style="margin-right: 20px;">
					<image :src="item.src"></image>
				</view>
			</u-scroll-list>
		</view>

		<view style="margin-top: 150rpx;">
			<u-row :gutter="8">
				<u-col :span="4" :offset="1">
					<u-button type="info">重选</u-button>
				</u-col>

				<u-col :span="4" :offset="2">
					<u-button color="#aaaaff">全选</u-button>
				</u-col>
			</u-row>
		</view>

		<u-collapse @change="change" @close="close" @open="open">
			<u-collapse-item v-for="file,file_index in picList" :key="file_index"
				:title="'[图' + (file_index+1) + ']' + file.showName" :name="file.remoteName">
				<text class="u-collapse-content" user-select="true">
					<text>图片大小：{{(file.size / 1024 / 1024).toFixed(2)}}MB \n</text>
					<text>文字数量：{{file.ocrResult.words_result_num}} \n</text>

					<text v-for="text,text_index in file.ocrResult.words_result" :key="text_index" user-select="true">
						<text>{{text.words}}</text>
						<text style="color: #00ba00 ">(可信度:{{text.probability.average.toFixed(2)*100}}%)</text>
						<text>\n</text>
					</text>
				</text>
			</u-collapse-item>

		</u-collapse>


		<!-- 		<view class="wrap" style="margin-top: 150rpx;">
		
			<view v-for="file,file_index in picList" :key="file_index">
				<view>{{file_index}}:云端文件名:{{file.remoteName}}</view>
				
			</view>	
		
		</view> -->

		<view style="margin-top: 150rpx;">
			<u-button @click="handleCompare" type="primary">确定</u-button>
		</view>


	</view>
</template>

<script>
	export default {
		data() {
			return {
				picList: [],
			}
		},
		onLoad() {
			let that = this
			let fileList = getApp().globalData.fileList
			fileList.forEach(file => {
				file.showName = file.remoteName.substring(0, 10) + '...' + file.remoteName.split('.')[1]
				file.src = file.thumb
				that.picList.push(file)
			})

		},

		methods: {
			handleCompare() {
				uni.$u.route('/pages/index/result');
			},
			open(e) {
				// console.log('open', e)
			},
			close(e) {
				// console.log('close', e)
			},
			change(e) {
				// console.log('change', e)
			}
		}
	}
</script>

<style>
	.wrap {
		padding: 40rpx;
	}
</style>
