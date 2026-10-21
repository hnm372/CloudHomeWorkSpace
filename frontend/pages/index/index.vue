<template>
	<view class="container">
		<u-notice-bar :text="noticeText" mode="closable"></u-notice-bar>
		<!-- 		<view class="center-css">
			<view class="card-wrap" @click="selectPicToCompare">
				<view style="width:100px;height:50px;">
					<view>
						<span style="font-size: 20px;">校对图片</span>
						<u-icon name="plus-circle" color="#dbdbdb" size="20"></u-icon>
					</view>
					<br /><span style="font-size: 10px;">支持格式 jpg/png</span>
				</view>
			</view>
		</view> -->
		<view style="display: inline;">
			<u-icon style="display: inline-block;padding-top: 1%;" name="photo" color="#6f6f6f" size="20"></u-icon>
			<span style="display: inline-block; font-size: 15px;">上传需要校对的图片 (png/jpg)</span>
		</view>


		<view class="media-container">
			<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple :maxCount="10"
				:previewFullImage="true">
			</u-upload>
		</view>
		<u-divider text="分割线" :dot="true"></u-divider>
		<view style="display: inline;">
			<u-icon style="display: inline-block;" name="file-text" color="#6f6f6f" size="20"></u-icon>
			<span style="display: inline-block; font-size: 15px;">输入需要校对的文字</span>
		</view>

		<view class="r-top">
			<textarea :maxlength="len" placeholder-style="color:rgba(153,153,153,1);" v-model="content"
				:placeholder="pl" />
			<view class="r-top-t">
				<view></view>
				<view> {{contentLength}}/{{len}}字</view>
			</view>
		</view>
		<span>上传校对文本</span>

		<lsj-upload ref="lsjUpload" childId="upload1" :width="width" :height="height" :option="option" :size="size"
			:formats="formats" :debug="debug" :instantly="instantly" @progress="onprogress" @change="onChange"
			@uploadEnd="onuploadEnd">
			<view class="btn" :style="{width: width,height: height}">选择文档</view>
		</lsj-upload>

		<view style="display: inline;">
			<u-icon style="display: inline-block;" name="info-circle" color="#8f8f8f" size="15"></u-icon>
			<span style="display: inline-block; font-size: 10px; color:#8f8f8f;">支持格式 word</span>
		</view>

		<view>
			<view>已选择的文件：{{wxFiles[0]?'':'无'}}</view>

			<!-- #ifdef MP-WEIXIN -->
			<view v-for="(item,index) in wxFiles" :key="index">
				<text>{{item.name}}</text>
				<text style="margin-left: 10rpx;">大小：{{item.size}}</text>
				<text style="margin-left: 10rpx;">状态：{{item.type}}</text>
				<text style="margin-left: 10rpx;">进度：{{item.progress}}</text>
				<view>
					<button @click="clear(item.name)">删除</button>
				</view>
			</view>
			<!-- #endif -->
		</view>

		<u-button @click="selectDocToCompare" type="info">上传文档<u-icon style="margin-left: 10rpx;" name="plus-circle"
				color="#8f8f8f" size="15">
			</u-icon>
		</u-button>

		<!-- ocr的结果 -->
		<!-- 		<br /><span>图片识别结果：{{ocrResText}}</span>
		<span>正确率：{{}}</span> -->
		<view style="margin-top: 50rpx;">
			<u-button  @click="startToCompare" type="primary" text="开始校对"></u-button>
			<!-- 提示信息 -->
			<u-toast ref="uToast"></u-toast>
		</view>

	</view>
</template>

<script>
	import {
		doLogin,
		doRegister,
		getUserInfo,
		doLogout
	} from '@/config/api.js'
	import {
		pathToBase64,
		base64ToPath
	} from '@/config/utils.js'

	export default {
		computed: {
			contentLength() {
				return this.content.length;
			}
		},
		data() {
			return {
				noticeText: `测试版本号:v${getApp().globalData.appVersion}`,
				// 用户变量
				isLoginFlag: false,
				userInfo: {
					username: '',
				},
				scrollTop: 0,
				refreshKey: 0,
				cover: '',
				href: 'https://uniapp.dcloud.io/component/README?id=uniui',
				numInLine: 5,
				recommendList: [
					1, 2, 3, 4, 5, 6, 7, 8, 9
				],
				loginModalShow: false,
				loginInfo: {
					'username': '',
					'password': ''
				},
				registerModalShow: false,
				registerInfo: {
					'username': '',
					'password': '',
					'repassword': '',
					'email': ''
				},
				captcha: '',
				// OCR
				ocrResText: '',
				content: '',
				// 文字输入框
				pl: '校对文字：',
				len: 200,
				// 图片上传
				uploadUrl: `${uni.$u.http.config.baseURL}/file/upload`,
				fileList1: [],
				// 文档上传：
				// 上传接口参数
				option: {
					// 上传服务器地址，此地址需要替换为你的接口地址
					url: `${uni.$u.http.config.baseURL}/file/upload`,
					// 上传附件的key
					name: 'file',
					// 根据你接口需求自定义请求头
					header: {},
					// 根据你接口需求自定义body参数
					formData: {
						// 'orderId': 1000
					}
				},
				// 选择文件后是否立即自动上传，true=选择后立即上传
				instantly: false,
				// 必传宽高且宽高应与slot宽高保持一致
				width: '100%',
				height: '80rpx',
				// 限制允许选择的格式，空串=不限制，默认为空
				formats: 'doc,docx',
				// 文件上传大小限制
				size: 10,
				// 文件回显列表
				files: new Map(),
				// 微信小程序Map对象for循环不显示，所以转成普通数组，不要问为什么，我也不知道
				wxFiles: [],
				// 是否打印日志
				debug: false,
			}
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop;
		},
		onReady() {},
		onShow() {
			// this.isLogin()
		},
		onLoad() {
			// Do not call the legacy /user/all endpoint: the backend has no such route.
			this.initDisplay()
		},
		methods: {
			initDisplay() {
				let windowWidth = 1080
				let that = this
				uni.onWindowResize((res) => {
					console.log('变化后的窗口宽度=' + res.size.windowWidth)
					// console.log('变化后的窗口高度=' + res.size.windowHeight)
					setTimeout(() => {
						that.setItemNumInLine(res.size.windowWidth)
					}, 1000)

				})
				uni.getSystemInfo({
					success: (res) => {
						// 注意这里获得的高度宽度都是px，需要转换rpx
						// console.log(res.screenHeight); // 屏幕高度，包含导航栏
						// console.log(res.screenWidth); // 屏幕宽度
						// console.log(res.windowHeight); // 可使用窗口高度，不包含导航栏
						// console.log(res.windowWidth); // 可使用窗口宽度
						// 可使用窗口高度，将px转换rpx
						// this.phoneHeight = (res.windowHeight * (750 / res.windowWidth))
						// console.log(this.phoneHeight)
						windowWidth = res.windowWidth
						that.setItemNumInLine(windowWidth)
					}
				})
			},
			isLogin() {
				this.isLoginFlag = Boolean(getApp().globalData.authToken)
			},
			toUserPage() {
				uni.$u.route('/pages/user/user');
			},
			toProcessPage() {
				uni.$u.route('/pages/index/process');
			},
			toResultPage() {
				uni.$u.route('/pages/index/result');
			},
			setItemNumInLine(windowWidth) {
				// let that = this
				// if (windowWidth < 650) {
				// 	that.numInLine = 1
				// } else if (windowWidth < 900 & windowWidth >= 650) {
				// 	that.numInLine = 2
				// } else if (windowWidth < 1260 & windowWidth >= 900) {
				// 	that.numInLine = 3
				// } else if (windowWidth < 1500 & windowWidth >= 1260) {
				// 	that.numInLine = 4
				// } else {
				// 	that.numInLine = 5
				// }
				// that.refreshKey += 1
				// console.log(that.numInLine, that.refreshKey)
			},
			showLoginModal() {
				this.loginModalShow = true
			},
			async LoginConfirm() {
				setTimeout(() => {
					// 1秒后自动关闭
					this.loginHandler()
					this.loginModalShow = false;
				}, 1000)
			},
			async loginHandler() {
				uni.$u.throttle(this.toNext, 500)
				// The login UI must still implement captcha input before this flow can work.
				const loginPayload = {...this.loginInfo, userAccount: this.loginInfo.username}
				let res = await doLogin(loginPayload)
				if (res && res.token) {
					getApp().globalData.authToken = res.token
					this.isLogin()
				}
			},
			showRegisterModal() {
				this.registerModalShow = true
			},
			async RegisterConfirm() {
				setTimeout(() => {
					// 3秒后自动关闭
					this.RegisterHandler()
					this.registerModalShow = false;
				}, 1000)
			},
			async RegisterHandler() {
				uni.$u.throttle(this.toNext, 500)
				// The registration UI must still implement captcha input before this flow can work.
				const registerPayload = {...this.registerInfo, userAccount: this.registerInfo.username}
				let res = await doRegister(registerPayload)
				this.$refs.uToast.show({
					type: 'success',
					message: "注册成功，请重新进行登录",
					iconUrl: 'https://cdn.uviewui.com/uview/demo/toast/success.png'
				})
			},
			showSettings() {

			},
			// 业务方法
			selectPicToCompare() {
				let that = this
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album', 'camera'], //从相册选择
					success: function(res) {
						that.ocrResult = '识别中……'
						let tempSrc = res.tempFilePaths[0]
						//#ifdef H5
						pathToBase64(tempSrc)
							.then(base64 => {
								base64 = base64.substring(base64.indexOf(',') + 1)
								that.ocrRequst(base64)
							})
							.catch(error => {
								console.error(error)
							})
						//#endif

						//#ifdef MP-WEIXIN
						uni.getFileSystemManager().readFile({
							filePath: tempSrc,
							encoding: 'base64',
							success: r => { // r.data 
								that.ocrRequst(r.data)
							}
						})
						//#endif
					}
				})
			},
			selectDocToCompare() {
				let that = this
				if(that.wxFiles.length == 0){
					that.$refs.uToast.show({
						type: 'error',
						icon: false,
						message: "请先选择一个文档",
						iconUrl: 'https://cdn.uviewui.com/uview/demo/toast/error.png'
					})
				}else{
					uni.showModal({
						title: '注意',
						content: '上传文档后将会覆盖已经输入的文字,点击确定后开始上传',
						confirmText: '确定',
						cancelText: '取消',
						success: function(res) {
							if (res.confirm) {
								console.log('用户点击确定');
								that.upload()
							} else if (res.cancel) {
								console.log('用户点击取消');
							}
						}
					})
				}
				
			
			},
			// 图片处理相关方法
			// 删除图片
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1)
			},
			// 新增图片
			async afterRead(event) {
				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
				let lists = [].concat(event.file)
				let fileListLen = this[`fileList${event.name}`].length
				lists.map((item) => {
					this[`fileList${event.name}`].push({
						...item,
						status: 'uploading',
						message: '上传中'
					})
				})
				for (let i = 0; i < lists.length; i++) {
					const result = await this.uploadFilePromise(lists[i].url)
					// 对result解析
					let json_result = JSON.parse(result)
					let json_data = JSON.parse(json_result["data"]["data"])

					let item = this[`fileList${event.name}`][fileListLen]
					this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
						status: 'success',
						message: result,
						url: result,
						remoteName: json_data.fileName,
						ocrResult: json_data.ocrResult
					}))
					fileListLen++
				}
			},
			uploadFilePromise(url) {
				let that = this
				return new Promise((resolve, reject) => {
					let a = uni.uploadFile({
						url: that.uploadUrl, // 仅为示例，非真实的接口地址
						filePath: url,
						name: 'file',
						formData: {
							user: 'test'
						},
						success: (res) => {
							setTimeout(() => {
								resolve(res.data)
							}, 1000)
						}
					});
				})
			},
			startToCompare() {
				let that = this
				//校验
				if (that.fileList1.length == 0) {
					that.$refs.uToast.show({
						type: 'error',
						icon: false,
						message: "未上传图片",
						iconUrl: 'https://cdn.uviewui.com/uview/demo/toast/error.png'
					})
				} else if (that.content == '') {
					that.$refs.uToast.show({
						type: 'error',
						icon: false,
						message: "未填写要校对的文本",
						iconUrl: 'https://cdn.uviewui.com/uview/demo/toast/error.png'
					})
				} else {

					uni.showModal({
						title: '提示',
						content: '点击确定将开始校对图文',
						confirmText: '确定',
						cancelText: '取消',
						success: function(res) {
							if (res.confirm) {
								console.log('用户点击确定');
								getApp().globalData.fileList = that.fileList1
								getApp().globalData.content = that.content
								that.toProcessPage()
							} else if (res.cancel) {
								console.log('用户点击取消');
							}
						}
					})

				}

			},
			// 某文件上传结束回调(成功失败都回调)
			onuploadEnd(item) {
				console.log(`${item.name}已上传结束，上传状态=${item.type}`);

				// 更新当前状态变化的文件
				this.files.set(item.name, item);

				// 演示上传完成后取服务端数据
				if (item['responseText']) {
					console.log('演示服务器返回的字符串JSON转对象');
					this.files.get(item.name).responseText = JSON.parse(item.responseText);
					this.content = this.files.get(item.name).responseText["data"]["text"]
				}

				// 微信小程序Map对象for循环不显示，所以转成普通数组，不要问为什么，我也不知道
				// #ifdef MP-WEIXIN
				this.wxFiles = [...this.files.values()];
				// #endif

				// 强制更新视图
				this.$forceUpdate();
				
				this.$refs.uToast.show({
					type: 'success',
					message: "文档解析成功",
					iconUrl: 'https://cdn.uviewui.com/uview/demo/toast/success.png'
				})

			},
			// 上传进度回调
			onprogress(item) {
				// 更新当前状态变化的文件
				this.files.set(item.name, item);

				// 微信小程序Map对象for循环不显示，所以转成普通数组，不要问为什么，我也不知道
				// #ifdef MP-WEIXIN
				this.wxFiles = [...this.files.values()];
				// #endif

				// 强制更新视图
				this.$forceUpdate();

			},

			// 文件选择回调
			onChange(files) {
				// 更新选择的文件
				this.files = files;
				// 强制更新视图
				this.$forceUpdate();

				// 微信小程序Map对象for循环不显示，所以转成普通数组，不要问为什么，我也不知道
				// #ifdef MP-WEIXIN
				this.wxFiles = [...this.files.values()];
				// #endif
			},
			// 手动上传
			upload() {
				// name=指定文件名，不指定则上传所有type等于waiting和fail的文件
				this.$refs.lsjUpload.upload();
			},
			// 移除某个文件
			clear(name) {
				// name=指定文件名，不传name默认移除所有文件
				this.$refs.lsjUpload.clear(name);
			},


			// --------------
		}

	}
</script>

<style lang="scss">
	.container {
		padding: 5px;
		font-size: 14px;
		line-height: 24px;
	}

	.card-actions {
		display: inline;
	}

	.action-icon {
		padding-top: 100rpx;
	}

	.card-actions-item-text {
		padding-bottom: 100rpx;
	}

	.box {
		width: 100px;
		height: 100px;
		background-color: #ffffff;
		border-radius: 15px;
	}

	.card-wrap {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 200px;
		height: 150px;
		border: 1px solid #373737;
	}

	.media-container {
		padding: 1%;
	}

	.r-top {
		padding: 10rpx;
		background: #FFFFFF;
		border-radius: 20rpx;
	}

	.r-top-t {
		display: flex;
		justify-content: space-between;
		font-family: PingFang SC;
		font-weight: 400;
		line-height: 20px;
		color: rgba(153, 153, 153, 1);
		opacity: 1;
	}

	.btn {
		height: 80rpx;
		background-color: #ffffff;
		color: #000000;
		border-radius: 10rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 28rpx;
	}
</style>
