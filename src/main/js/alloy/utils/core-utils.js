import _ from 'lodash';
import * as Redux from 'redux';

let defaultList = val => {
	if(_.isNil(val)) {
		return [];
	}

	return val;
};

let defaultMap = val => {
	if(_.isNil(val)) {
		return {};
	}

	return val;
};

let defaultString = string => {
	if(!isDefined(string)) {
		return '';
	}
	return string;
};

let isDefined = obj => {
	return !_.isNull(obj) && !_.isUndefined(obj);
};

let isBlank = str => {
	if (str === null || str === undefined) str = '';
	return (/^\s*$/).test(str);
};

let valueAt = (array, index) => {
	if(!isDefined(array) || !isDefined(index)) {
		return null;
	}
	if(!(array.length > index)) {
		return null;
	}

	return array[index];
};

let valuate = element => {
	if(_.isFunction(element)) {
		return element();
	}

	return element;
};

let promiseContext = () => {
	let context = {};
	context.promise = new Promise(function (resolve, reject) {
		context.resolver = {
			resolve: resolve,
			reject: reject
		};
	});
	return context;
};

_.mixin({ defaultList, defaultMap, defaultString, isDefined, isBlank, valueAt, valuate, promiseContext });

export function extendComponent(field, additionalProps) {
	let props = {...field.props, ...additionalProps};
	return {...field, props};
}

export function combineReducers(rawReducers, initialState = {}) {
	let reducers = _.map(rawReducers, reducer => _.pickBy(reducer, (value, key) => 'initialize' !== key));
	let reducerMap = {};

	for(let reducer of reducers) {
		_.merge(reducerMap, reducer);
	}

	for (let key of _.keys(initialState)) {
		if (_.isNil(reducerMap[key])) {
			reducerMap[key] = (state = {}) => state;
		}
	}

	return Redux.combineReducers(reducerMap);
}