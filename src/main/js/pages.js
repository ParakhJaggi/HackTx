import _ from 'lodash';

import React from 'react';
import {Link} from 'react-router-dom';
import {connect} from 'react-redux';
import * as Users from 'js/users';
import * as Login from 'js/login';
import * as Game from 'js/game';
import garlicGambler from '../resources/images/garlicGambler.png';

const garlicStyle = {
    width: 900,
    height: 900,
    textAlign: 'center',
	backgroundRepeat: 'no-repeat',
	repeat: 'no-repeat'
};

export class NavBar1 extends React.Component {
	logoutClick = () => {
		return this.props.logout();
	};

	render() {
		return (
			<nav style={{backgroundColor: '#333'}} className="navbar navbar-expand-md">
				<a className="navbar-brand" href="#/game" style={{color:'white'}} >Garlic Gambler </a>
				<button className="navbar-toggler" type="button" data-toggle="collapse"
						data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
						aria-expanded="false" aria-label="Toggle navigation" style={{color:'white'}}>
					<span className="navbar-toggler-icon" style={{color:'white'}}></span>
				</button>

				<div className="collapse navbar-collapse justify-content-end" id="navbarCollapse" >
					<ul style={{color:'white', float:'right'}} className="nav navbar-nav navbn ml-auto" >
						{_.isDefined(this.props.user) &&
						<li className="nav-item" style={{borderRight:'1.5px solid white'}}>
							<a className="nav-link" style={{color: 'white'}}> Hi, {this.props.user.name} </a>
						</li>
						}
						<li className="nav-item ">
							<a className="nav-link" href="#/game" style={{color:'white'}} > Play Game! <span className="sr-only">(current)</span></a>
						</li>

						{!_.isDefined(this.props.user) &&
						<li className="nav-item">
							<a className="nav-link" href="/#/register" style={{color: 'white'}}> Register </a>
						</li>
						}
						{ !_.isDefined(this.props.user) &&
						<li className="nav-item">
							<a className="nav-link" href="/#/login" style={{color:'white'}} > Login </a>
						</li>
						}

						{_.isDefined(this.props.user) &&
						<li className="nav-item">
							<a className="nav-link" href="/#/" style={{color: 'white'}} onClick={this.logoutClick}> Logout </a>
						</li>
						}
					</ul>
				</div>
			</nav>
		);
	}
}

NavBar1 = connect(
	state => ({
		user: Users.State.getUser(state)
	}),
	dispatch => ({
		logout: () => dispatch(Users.Actions.logout())
	})
)(NavBar1);

export class Home extends React.Component {
	render() {
		return (
			<div className="container padded">
                <img src={garlicGambler} style={garlicStyle}/>
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
							<a className="d-block small" href="/#/game">Play Game</a>

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