import _ from 'lodash';

import React from 'react';
import {Link} from 'react-router-dom';
import {connect} from 'react-redux';
import * as Users from 'js/users';
import * as Login from 'js/login';
import * as Game from 'js/game';

export class Home extends React.Component {
	render() {
		return (
			<div className="container padded">
				This is the home page.

				<ul>
					<li><Link to="/register">Register</Link></li>
					<li><Link to="/login">Login</Link></li>
					<li><Link to="/page-1">Page 1</Link></li>
					<li><Link to="/game">Game</Link></li>
				</ul>
			</div>
		);
	}
}

export class RegisterPage extends React.Component {
	render() {
		return (
			<div className="container padded">
				<div className="row">
					<div className="col-6 offset-md-3">
						<h2>Register</h2>
						<hr/>
						<Login.RegistrationForm/>
						<div className="text-center">
							<a className="d-block small" href="/#/login">Login</a>

						</div>
					</div>

				</div>
			</div>
		);
	}
}

export class LoginPage extends React.Component {
	render() {
		return (
			<div className="container padded">
				<div className="row">
					<div className="col-6 offset-md-3">
						<h2>Login</h2>
						<hr/>
						<Login.LoginForm/>
						<div className="text-center">
							<a className="d-block small" href="/#/page-1">Play Game</a>

						</div>

					</div>
				</div>
			</div>
		);
	}
}

class Page1 extends React.Component {
	render() {
		return (
			<div className="container padded">
				This is page 1.

				{_.isDefined(this.props.authentication) &&
				<div>{this.props.authentication['access_token']}</div>
				}

				{_.isDefined(this.props.user) &&
				<div>Welcome, {this.props.user.principal}!</div>
				}
			</div>
		);
	}
}

Page1 = connect(
	state => ({
		authentication: Users.State.getAuthentication(state),
		user: Users.State.getUser(state)
	})
)(Page1);

export {Page1};

export class GamePage extends React.Component {
	render() {
		return (
			<div className="container padded">
				<Game.Game/>
			</div>
		);
	}
}