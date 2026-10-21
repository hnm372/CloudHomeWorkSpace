<template>
	<view style="padding: 1%;">
		<view>
			图片校对结果
		</view>
		<view class="wrap">
			<u-scroll-list :indicator="true" indicatorColor="#fff0f0" indicatorActiveColor="#f56c6c">
				<view v-for="(item, index) in picList" :key="index" style="margin-right: 20px;">
					<image :src="item.src"></image>
				</view>
			</u-scroll-list>
		</view>
		<view>文字校对结果</view>
		<view class="wrap">
			<!-- 			<view style="border: 1px; height: 200px; background-color: #9e9e9e;">
			</view> -->

			<u--textarea style=" background-color: #d5d5d5; " v-model="compareTextResult" placeholder="校对结果" disabled
				count autoHeight maxlength="300"></u--textarea>


		</view>
		<view>
			<u-action-sheet :actions="exportActionList" :title="exportActionTitle" :show="showExportList"
				@select="exportHandler"></u-action-sheet>
			<u-button style="margin-top: 5%;" @click="showExportList = true" type="primary">导出结果</u-button>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				errorCount: 0,
				picList: [],
				showExportList: false,
				exportActionTitle: '选择结果导出格式',
				exportActionList: [{
						id: 1,
						name: '图片',
						// subname: "选项一描述",
						color: '#00aaff',
						fontSize: '20'
					},
					{
						id: 2,
						name: '文档',
						// subname: "选项一描述",
						color: '#aaaaff',
						fontSize: '20'
					},
					{
						id: 0,
						name: '取消', //开启后文字不显示
						color: '#4f4f4f'
					}
				],
				compareTextResult: ''
			}
		},
		onLoad() {
			// this.showResult()


			let that = this
			let fileList = getApp().globalData.fileList
			let content = getApp().globalData.content
			// that.compareTextResult = getApp().globalData.content
			fileList.forEach(file => {
				that.picList.push({
					'src': file.thumb
				})
			})

			let content_list = content.split('\n')
			let text_count = 0
			let flag = false

			let result_text = ''
			fileList.forEach((file, i) => {
				file.ocrResult.words_result.forEach((text, j) => {
					text_count += 1
					content_list.forEach((content, k) => {

						if (content.length > 0 && text.words.includes(content)) {

							result_text += `文本【${content}】对应上：第${i + 1}张图片的第${j + 1}个文本。 \n`

						}


					})
				})
			})

			that.compareTextResult = `图片里共${text_count}条文本，需要校对${content_list.length}条文本 \n` + result_text

			uni.showModal({
				title: '提示',
				content: "校对完成",
				confirmText: '知道了',
				showCancel: false,
				success: function(res) {
					if (res.confirm) {
						console.log('用户点击确定');
					} else if (res.cancel) {
						console.log('用户点击取消');
					}
				}
			})

		},
		methods: {
			showResult() {
				let that = this
				uni.showModal({
					title: '提示',
					content: `一共发现了${that.errorCount}处错误`,
					confirmText: '知道了',
					showCancel: false,
					success: function(res) {
						if (res.confirm) {
							console.log('用户点击确定');
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				})
			},
			exportResult() {

			},
			exportHandler(index) {
				this.showExportList = false
				console.log('导出:', index.name)
				if (index.id != 0) {
					uni.$u.route('/pages/index/success');
				}

			},

		}
	}
</script>

<style>
	.wrap {
		padding: 40rpx;
	}
</style>
