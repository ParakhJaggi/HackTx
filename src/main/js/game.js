import _ from 'lodash';

import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import * as Users from 'js/users';

class Game extends React.Component {
	render() {
		return (
			<div className="container padded">
				Inside the game.
				<button className={'btn btn-primary'}>HERE I AM</button>
			</div>
		);
	}
}

Game = connect(
	state => ({

	}),
	dispatch => ({
		register: user => dispatch(Users.Actions.register(user))
	})
)(Game);

export { Game };

