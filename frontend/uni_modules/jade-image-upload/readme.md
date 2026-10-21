#jade-image-upload 图片上传组件

写插件不易，好用的话请5星好评，向身边朋友推荐吧ヾ(*´▽‘*)ﾉ。

**注：本组件目前兼容微信小程序、H5，其它平台未实际测试过。**

*************

##完整示例
**注：建议使用下载后的示例，不要直接复制本示例以免出现莫名bug**
**下面的this.uploadTask的onProgressUpdate()方法 不知道为什么每次上传都被篡改成this.uploadTask.Update()**
```
<template>
	<view class="media-container">
		<jade-image-upload
		 :list="media"
		 :control="control"
		 :columnType="columnType"
		 :maxCount="maxCount"
		 :compressSize="compressSize"
		 :compressQuality="compressQuality"
		 :compressWidth='compressWidth'
		 :imageSize="imageSize"
		 @chooseFile="chooseFile"
		 @imgDelete="mediaDelete">
		</jade-image-upload>
		<!-- 数据变化的JSON  -->
		<view style="padding: 20rpx;box-sizing: border-box;display: flex;flex-direction: column;">
			图片上传：
			<!-- #ifdef H5 -->
			<view style="padding: 20rpx;word-break: break-all;">{{media.length ? media: '暂无数据'}}</view>
			<!-- #endif -->
			<!-- #ifndef H5  -->
			<view style="padding: 20rpx;word-break: break-all;">{{media.length ? JSON.stringify(media): '暂无数据'}}</view>
			<!-- #endif -->
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				control: true,
				columnType: 'other',
				maxCount: 6,
				compressSize: 0.2,
				imageSize: 2,
				compressQuality: 0.8,
				compressWidth: 375,
				
				uploadTask: null,
				media: [], //数据源
			};
		},
		methods: {
			//上传
			chooseFile(e) {
				this.uploadFileToServe(e)
			},
			//中断上传并删除
			mediaDelete(e) {
				this.uploadTask ? this.uploadTask.abort() : ''
				this.media.splice(e,1)
			},
			//上传逻辑处理
			uploadFileToServe(urlList) {
				if (!urlList || urlList.length <= 0) {
					return
				};
				//以七牛云为例,可根据实际需求灵活调整
				uni.request({
					url: 'qiniu', //后端接口，仅为示例，并非真实接口地址。
					method: 'GET',
					success: (res) => {
						let token = res.data.data; //拿到上传七牛所必须的token
						urlList.forEach((item) => {
							this.uploadTask = uni.uploadFile({
								url: 'qiniu', //七牛上传接口，仅为示例
								filePath: item.src,
								name: 'file',
								formData: {
									'token': token
								},
								success: (res) => {
									let data = JSON.parse(res.data) //七牛返回的数据
									if (!data.data.url) {
										item.status = 'error'
										item.progress = '上传失败'
									} else {
										item.status = 'success'
										item.progress = '上传成功'
										item.src = data.data.url
									}
								}
							});
							this.uploadTask.onProgressUpdate((res) => {
								item.percent = res.progress
								this.media.splice(item.index,1,item)
							})
						})
					}
				});
			},
		}
	}
</script>

<style lang="scss" scoped>
	.media-container {
		padding: 30rpx 26rpx;
		box-sizing: border-box;
	}
</style>



```
## 属性介绍
| 名称                        | 类型              | 默认值                    | 描述 |
| ----------------------------|---------------   | ---------                 |------- |
| list                        | Array            | []                        |数据源|
| control                     | Boolean          | true                      |是否显示上传控件|
| activeColor                 | String           | #25C55A                   |进度条颜色|
| deleteBtn                   | Boolean          | true                      |是否显示删除按钮|
| columnType                  | String   		 | normal                    | normal，other两种类型，other是部分美化后的效果|
| maxCount                    | Number           | 3                         | 上传最大数量|
| compressSize                | Number           | 5                         | 照片超出压缩大小 MB|
| imageSize                   | Number           | 20                        | 照片限制大小 MB|
| compressQuality             | Number           | 0.99                      | 照片压缩质量 （取值范围0~1）|
| compressWidth               | Number           | 750                       | 照片压缩宽度 px（仅h5支持）|

## 事件介绍
| 名称               | 说明            
| -----------------  |------------------
| chooseFile         | 返回的值：对象集合
| imgDelete         | 返回的值：删除的图片下标，基本不用。主要为了中断上传处理
