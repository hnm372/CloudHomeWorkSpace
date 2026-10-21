<template>
	<view>
		<u-icon name="checkmark" color="#2979ff" size="400"></u-icon>
		<view class="center-css" style="font-size: 30px;">导出成功!</view>
		<view class="center-css" style="font-size: 15px; margin-top: 5%; color: #8f8f8f;">{{resultText}}</view>
		<view class="center-css" style="padding: 5% 5% 0 5%;">
			<u-button style="margin-top: 20%;" @click="toIndexPage" type="info">离开</u-button>
		</view>
		
		<view class="center-css" style="padding: 5%;">
			<u-button style="" @click="" type="primary">校对其它</u-button>
		</view>

	</view>
</template>

<script>
	export default {
		props: {
			exportType: {
				type: String,
				default: '图片'
			},
		},
		data() {
			return {
				resultText:'',
				fileDir:'我的相册'
			}
		},
		onLoad() {
			this.resultText = this.exportType + '结果已存至:' + this.fileDir
		},
		methods: {
			toIndexPage(){
				uni.$u.route('/pages/index/index');
			}

		}
	}
</script>

<style lang="scss">

</style>
