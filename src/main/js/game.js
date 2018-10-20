import _ from 'lodash';

import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import * as Users from 'js/users';
import * as Deck from 'js/decks';


class Game extends React.Component {
	constructor() {
		super();
		this.deck = new Deck.Deck();
	}
	render() {
		return (
			<div className="container padded">
				Inside the game.
				<button className={'btn btn-primary'}>HERE I AM</button>

				<div>Deck Size: {this.deck.size()}</div>

				<div>Deck Card: {this.deck.printCards()}</div>

				<img src={this.deck.printCard()} alt={'A Card'}/>
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

