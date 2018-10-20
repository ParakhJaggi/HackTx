import _ from 'lodash';
import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import createLogger from 'redux-logger';
import thunkMiddleware from 'redux-thunk';
import { reducer as formReducer } from 'redux-form';
import axios from 'axios';

import Index from 'js/index';
import * as Users from 'js/users';
import * as Utils from 'js/alloy/utils/core-utils';

import 'styles/main.scss';

const reducers = [
	{form: formReducer},
	Users.Reducers
];

const reducer = Utils.combineReducers(reducers);
const store = createStore(reducer, {authentication: null, user: null}, applyMiddleware(thunkMiddleware, createLogger()));

axios.defaults.headers.post['Content-Type'] = 'application/json';
axios.defaults.headers.put['Content-Type'] = 'application/json';

axios.interceptors.request.use(request => {
	let authentication = Users.State.getAuthentication(store.getState());
	if(_.isDefined(authentication)) {
		request.headers.common['Authorization'] = 'Bearer ' + authentication['access_token'];
	}

	return request;
}, error => Promise.reject(error));

axios.interceptors.response.use(response => response.data, error => Promise.reject(error));

const mountNode = document.querySelector('#main');
ReactDOM.render(<Provider store={store}><Index /></Provider>, mountNode);