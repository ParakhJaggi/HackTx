import React from 'react';
import {HashRouter, Route} from 'react-router-dom';

import * as Pages from 'js/pages';
import {NavBar1} from 'js/pages';

export default class Index extends React.Component {
	render() {
		return (
			<HashRouter>
				<div>
					<NavBar1/>
					<Route exact path="/" component={Pages.Home}/>
					<Route exact path="/register" component={Pages.RegisterPage}/>
					<Route exact path="/login" component={Pages.LoginPage}/>
					<Route exact path="/page-1" component={Pages.Page1}/>
					<Route exact path="/game" component={Pages.GamePage}/>
				</div>
			</HashRouter>
		);
	}
}