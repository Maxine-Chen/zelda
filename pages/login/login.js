// pages/login/login.js

var app = getApp();
Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		usernum: '',
		password: '',
		usernume: '',
		login: true,
	},

	// 用户名输入
	onUsernameInput(e) {
		this.setData({
			username: e.detail.value
		});
	},

	// 账号输入
	onUsernumInput(e) {
		this.setData({
			usernum: e.detail.value
		});
	},

	// 密码输入
	onPasswordInput(e) {
		this.setData({
			password: e.detail.value
		});
	},

	// 处理登录
	handleLogin() {
		const loginData = {
			usernum: this.data.usernum,
			password: this.data.password
		};
		if (!loginData.usernum || !loginData.password) {
			wx.showToast({
				title: '请输入账号和密码',
				icon: 'none'
			});
			return;
		}

		wx.request({
			url: app.globalData.baseUrl + '/appinfo/sysUser/login',
			method: 'POST',
			data: loginData,
			success: (res) => {
				if (res.data.code === 200) {
					// 保存用户信息到全局变量
					app.globalData.user = {
						id: res.data.data.userId,
						name: res.data.data.userName,
					};
					app.globalData.isLoggedIn = true;
					console.log("登录成功");
					console.log('登录成功后globalData:', app.globalData);
					wx.showToast({
						title: '登录成功',
						icon: 'success'
					});

					// 返回上一页
					wx.navigateBack();
				} else {
					wx.showToast({
						title: res.data.msg,
						icon: 'none'
					});
				}
			},
			fail: (err) => {
				wx.showToast({
					title: '网络错误',
					icon: 'none'
				});
			}
		});
	},

	handleRegister() {
		const registerData = {
			username: this.data.username,
			password: this.data.password
		};
		if (!registerData.username || !registerData.password) {
			wx.showToast({
				title: '请输入用户名和密码',
				icon: 'none'
			});
			return;
		}
		wx.request({
			url: app.globalData.baseUrl + '/appinfo/sysUser/register',
			method: 'POST',
			data: registerData,
			success: (res) => {
				if (res.data.code === 200) {
					// 保存用户信息到全局变量
					app.globalData.user = {
						id: res.data.data.userId,
						name: res.data.data.userName,
					};
					app.globalData.isLoggedIn = true;
					console.log("注册成功");
					console.log('注册成功后globalData:', app.globalData);
					wx.showToast({
						title: '注册成功',
						icon: 'success'
					});

					// 返回上一页
					wx.navigateBack();
				} else {
					wx.showToast({
						title: res.data.msg,
						icon: 'none'
					});
				}
			},
			fail: (err) => {
				wx.showToast({
					title: '网络错误',
					icon: 'none'
				});
			}
		});
	},

	// 切换到注册
	onRegister() {
		this.setData({
			login: false
		})
	},

	// 切换到登录  
	onLogin() { 
		this.setData({
			login: true
		})
	},
	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad(options) {

	},

	/**
	 * 生命周期函数--监听页面初次渲染完成
	 */
	onReady() {

	},

	/**
	 * 生命周期函数--监听页面显示
	 */
	onShow() {

	},

	/**
	 * 生命周期函数--监听页面隐藏
	 */
	onHide() {

	},

	/**
	 * 生命周期函数--监听页面卸载
	 */
	onUnload() {

	},

	/**
	 * 页面相关事件处理函数--监听用户下拉动作
	 */
	onPullDownRefresh() {

	},

	/**
	 * 页面上拉触底事件的处理函数
	 */
	onReachBottom() {

	},

	/**
	 * 用户点击右上角分享
	 */
	onShareAppMessage() {

	}
})