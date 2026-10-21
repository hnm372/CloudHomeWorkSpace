<template>
	<view>
		<view style="text-align: right; margin-right: 1%;" @click="logoutHandler">退出当前账号</view>
		<view class="common-back">
			<u-row :gutter="2">
				<u-col span="4">
					<u-image mode='aspectFit' width="100%" height="230rpx" src="@/static/touxiang.png"
						customStyle="padding:2%"></u-image>
				</u-col>
				<u-col span="4">姓名：{{userInfo.username}}<br />
					学校：ECUST<br />
					年级：大一<br />
					专业：计算机<br />
					邮箱：{{userInfo.email}}
				</u-col>
				<u-col span="4">
					积分：39<br />
					学习时长：72h<br />
				</u-col>
			</u-row>

		</view>



		<view class="common-back">
			<view @click="learnIconTypeChange">
				<u-button text="学习记录" type="primary" style="padding: 1%; padding-top: 10rpx;"></u-button>
				<uni-icons class="right-end" :type="learnIconType" size="15"></uni-icons>
			</view>
			<view v-if="learnIconType == 'bottom'" :key="refreshKey">
				<uni-section title="" type="line">
					<view v-for="(item,index) in recordList" v-if="index%numInLine==0" :key="index">
						<u-row style="padding: 1%;">
							<view v-for="i in numInLine" :key="i" v-if="recordList[i-1+index] != null">
								<u-col :span="12">
									<uni-card :cover="cover" @click="toDetailPage">
										<u-image mode='aspectFit' width="100%" height="400rpx"
											src="@/static/class1.png">
										</u-image>
										<text class="uni-body">【体系课】吃透前端工程化，大厂级实战项目以战带练</text>
										<text>{{recordList[i-1+index]}}</text>
										<u-row>
											<u-col span="4">
												<u-button text="当前章节:3-1" type="primary"></u-button>
											</u-col>
											<u-col span="4" offset="4">
												<u-button text="学习进度:3%" type="primary"></u-button>
											</u-col>


											<!-- <u-col :span="4">
												<view class="card-actions-item" @click="actionsClick('分享')">
													<uni-icons type="pyq" size="18" color="#999"></uni-icons>
													<text class="card-actions-item-text">分享</text>
												</view>
											</u-col>
											<u-col :span="4">
												<view class="card-actions-item" @click="actionsClick('点赞')">
													<uni-icons type="heart" size="18" color="#999"></uni-icons>
													<text class="card-actions-item-text">点赞</text>
												</view>
											</u-col>
											<u-col :span="4">
												<view class="card-actions-item" @click="actionsClick('评论')">
													<uni-icons type="chatbubble" size="18" color="#999"></uni-icons>
													<text class="card-actions-item-text">评论</text>
												</view>
											</u-col> -->
										</u-row>
									</uni-card>
								</u-col>
							</view>
						</u-row>
					</view>
				</uni-section>
			</view>
		</view>

		<view class="common-back">
			学习路线推荐
			<view>


				<view v-for="(item,index) in resourseList" v-if="index%resourseNumInLine==0" :key="index">
					<u-row style="padding: 1%;">
						<view v-for="i in resourseNumInLine" :key="i" v-if="resourseList[i-1+index] != null">
	
								<uni-card :cover="cover" @click="onC" :class="{routeSelected:resourseList[i-1+index].selected}" >
									<view class="moduleIcon">
										<u-image mode='aspectFit' height="50rpx" width="100%"
											src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAQAAADZc7J/AAACm0lEQVR42p2VTWxMURTHb6v6wVCJNNKNhbahE0kb0QhREj8sLIQgItLEAgu6sWIlkdjURmIhdqKiQkTiIyFCfWzUd4Ih8V2qilGVEVPtdMb/zdz5eNPpyzz3JC/n3Xv///fOOf97rjGugWNVNLCGvZzkAi95x1VOc5CNzGe6s+4xkuCVnOc9EcZI5FicKH1000b1BCSaLqeVLn65gPkWFcm6An+iiUaOMegJzpJcZJmLQi+zuVEUOG2vWJKhkFPNKV9wxx7onx0KPSrpIOabIMEValME7YoqOx0hzEjSG5P3VX6Mfj4ybKvxXW8jdu8RygwBbubAh9nJYp4n/R+spZmHgrRST49d38JCXtvdb5lnJI/eHII/yu80gRz/m9bKuS2CpczhbmZ9JiG7e4Rdhj2MehLcktfPJxuCmyBBl+GsKzHOhoBAEQnqjfKcIvjCZ/4WJAgZ7ucRLFdag7TImiXsSoUQ1lyQJwUJxgyXCxBMURgBplIqijsMUKe3HqvCTTRJRun9YSMN5IdQRSePJJRrAlbkEcRV2L5MGRNcN6xiyDOJ+QSDms8SdBpm8dgHwTBblZtndvcQmx0l7ssRshNjkKep+PR39dxLCqnRJjHKBuba9QQnqHIIZnAmp3EMSLapkjkS7tU3YypiVsrZdafM9jQ2KGV+j9Jvid6Q6YMr+OAL3s8O5SenoZSwgHOuU+llIWWnZHxTC7DNnkMv6+O4qlOosSZDqVMD71b+4xM0sg7psAzPxm4k4Ra2c5gXLvBPDinZ3veCi2gSRzPgUR2p1UwuEmwJFinadMb3U1P0ty28QldMqudcUosr9QG2eVgvoSR0xe12rjKfQ/Aa9aGo7oqmcdUu8vvtOm9t6gvmP4bgtRyYQCoFxj8xs/3cSj+ZBQAAAABJRU5ErkJggg==">
										</u-image>
										<view class="moduleName">
											HelloWord
											<text>{{resourseList[i-1+index].id}}</text>
										</view>
									</view>
								</uni-card>

						</view>
					</u-row>
				</view>

			</view>

		</view>
	</view>
</template>

<script>
	import {
		doLogout
	} from '@/config/api.js';
	export default {
		data() {
			return {
				refreshKey: 0,
				userInfo: getApp().globalData.userInfo,
				learnRecordShow: false,
				learnIconType: 'forward',
				recordList: [
					1, 2, 3, 4, 5, 6, 7, 8, 9
				],
				numInLine: 5,
				resourseNumInLine: 10,
				learnRoute: [],
				resourseList: [{
					id:1,
					selected:true
				}, {
					id:2,
					selected:false
				}, ],

			}
		},
		onLoad() {
			// this.initDisplay()
		},
		onShow() {
			if (getApp().globalData.userTTL == 0) {
				uni.$u.route('/');
			}
		},
		methods: {
			toDetailPage() {
				uni.$u.route('/pages/detail/detail');
			},
			initDisplay() {
				let windowWidth = 1080
				let that = this
				uni.onWindowResize((res) => {
					console.log('变化后的窗口宽度=' + res.size.windowWidth)
					// console.log('变化后的窗口高度=' + res.size.windowHeight)
					that.setItemNumInLine(res.size.windowWidth)

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
			async logoutHandler() {
				let res = await doLogout()
				getApp().globalData.authToken = ''
				getApp().globalData.userInfo = {}
				getApp().globalData.userTTL = 0
				uni.$u.route('/');
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
			learnIconTypeChange() {
				if (this.learnIconType == 'forward') {
					this.learnIconType = 'bottom'
				} else {
					this.learnIconType = 'forward'
				}
			}
		}
	}
</script>

<style>
	.common-back {
		background-color: #e5e5e5;
		margin: 1%;
	}

	.right-end {
		position: absolute;
		right: 0;
		margin-right: 1%;
	}

	.moduleIcon {
		display: inline-block;
		margin: 8px;
		padding: 4px 8px;
		text-align: center;
		border: 1px dashed lightGray;
		cursor: pointer;
		min-width: 80px;
		position: relative;
	}
	
	.routeSelected{
		background-color: #00ffff;
	}
</style>
