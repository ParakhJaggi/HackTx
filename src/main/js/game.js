import _ from 'lodash';

import React from 'react';
import {Link} from 'react-router-dom';
import {connect} from 'react-redux';
import * as Bessemer from 'js/alloy/bessemer/components';
import * as Users from 'js/users';
import * as Deck from 'js/decks';
import * as Table from 'js/table';
import {Rank, Suit} from 'js/cards';
import BlackJackTitle from '../resources/images/blackjack.png';
import CardDeck from '../resources/images/blackjack.png';
import * as ReduxForm from 'redux-form';

const centerStyle = {textAlign: 'center'};

class Game extends React.Component {
	constructor(props) {
		super(props);
		this.deck = new Deck.Deck();
		console.log('DECK SIZE ' + this.deck.size());
		// this.state = {
		// 	dealer: {
		// 		hand: [],
		// 	},
		// 	user: {
		// 		hand: [],
		// 		balance: 0,
		// 	}
		// };
	}

	shuffleDeck = () => {
		console.log('Shuffling deck... ' + this.deck === null);
		this.deck.shuffle();
		this.forceUpdate();
	};

	restart = () =>  {
		this.deck = new Deck.Deck();
		this.forceUpdate();

	};

	hit = () =>  {

	};

	stay = () =>  {

	};

	addToBalance = () =>  {
		let u = this.props.user;
		console.log('Balance before update: ' + u.balance);
		this.props.updateBalance(10);
		setTimeout(this.forceUpdate, 1500);
		u = this.props.user;
		console.log('Balance after update: ' + u.balance);
	};

	render() {
		let { handleSubmit, submitting } = this.props;

		return (
			<div className="container padded">
				<div style={centerStyle}><img src={BlackJackTitle}/></div>

				<Table.Table/>

				<div>Deck Size: {this.deck.size()}</div>
				<div>Full deck of cards...</div>

				<button className={'btn btn-primary'} onClick={this.addToBalance}>Add 10 to Balance</button>
				<br/>

				<button className={'btn btn-success'} onClick={this.shuffleDeck}>Shuffle Cards</button>
				<button className={'btn btn-secondary'} onClick={this.hit}>Hit</button>
				<button className={'btn btn-secondary'} onClick={this.stay}>Stay</button>
				<button className={'btn btn-danger'} onClick={this.restart}>Restart</button>

				{this.deck.showDeckImages()}
			</div>
		);
	}
}

Game = connect(
	state => ({
		user: Users.State.getUser(state)
	}),
	dispatch => ({
		updateBalance: val => dispatch(Users.Actions.updateBalance(val))
	})
)(Game);

export {Game};

