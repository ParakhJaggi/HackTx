import Cookies from 'universal-cookie';
import axios from 'axios';

export function register(user) {
	return axios.post('/api/user/register', {
		principal: user.principal,
		password: user.password,
		balance: user.balance,
	});
}

export function authenticate(username, password) {
	return axios(
		{
			method: 'post',
			url: '/oauth/token',
			params: {
				'grant_type': 'password',
				username,
				password
			},
			auth: {
				username: 'hacktx-app',
				password: 'hacktx-app-secret'
			}
		}
	);
}

export function updateBalance(val) {
	return axios.post('/api/user/update_balance/' + val);
}

export function getUserDetails() {
	return axios.get('/api/user');
}

let State = {};

State.getAuthentication = state => {
	return state.authentication;
};

State.getUser = state => {
	return state.user;
};

export {State};

let Actions = {};

Actions.Types = {
	SET_AUTHENTICATION: 'SET_AUTHENTICATION',
	SET_USER: 'SET_USER'
};

Actions.updateBalance = val => {
	return (dispatch) => {
		return updateBalance(val).then(() => {
			return getUserDetails().then(user => {
				dispatch(Actions.setUser(user));
			});
		});
	};
};

Actions.register = user => {
	user.balance = 0;
	return (dispatch) => {
		return register(user).then(() => {
			return dispatch(Actions.authenticate(user.principal, user.password));
		});
	};
};

Actions.authenticate = (username, password) => {
	return (dispatch) => {
		return authenticate(username, password).then(
			authentication => {
				dispatch(Actions.setAuthentication(authentication));

				return getUserDetails().then(user => {
					dispatch(Actions.setUser(user));
				});
			}
		);
	};
};

Actions.logout = () => {
	return (dispatch) => {
		// Reset all User Action states
		dispatch(Actions.setAuthentication(null));
		dispatch(Actions.setUser(null));
		const cookies = new Cookies();
		cookies.remove('authentication');
		cookies.remove('user');
	};
};

Actions.setAuthentication = authentication => {
	// Set authentication cookie
	const cookies = new Cookies();
	cookies.set('authentication', authentication, { path: '/' });
	return {type: Actions.Types.SET_AUTHENTICATION, authentication};
};

Actions.setUser = user => {
	// Set user cookie
	const cookies = new Cookies();
	cookies.set('user', user, { path: '/' });
	return {type: Actions.Types.SET_USER, user};
};

export {Actions};

let Reducers = {};

Reducers.authentication = (authentication = null, action) => {
	switch (action.type) {
		case Actions.Types.SET_AUTHENTICATION: {
			return action.authentication;
		}
		default: {
			return authentication;
		}
	}
};

Reducers.user = (user = null, action) => {
	switch (action.type) {
		case Actions.Types.SET_USER: {
			return action.user;
		}
		default: {
			return user;
		}
	}
};

export {Reducers};