import _ from 'lodash';

import React from 'react';
import {Link} from 'react-router-dom';
import {connect} from 'react-redux';
import * as Bessemer from 'js/alloy/bessemer/components';
import * as Users from 'js/users';
import * as Deck from 'js/decks';
import {Rank, Suit} from 'js/cards';
import BlackJackTitle from '../resources/images/blackjack.png';
import CardDeck from '../resources/images/deck_small.jpg';
import * as ReduxForm from 'redux-form';
import cloth from '../resources/images/poker_cloth.jpg';

const tableStyle = {
	width: 1000,
	height: 750,
	backgroundRepeat: true,
	backgroundImage: `url(${cloth})`,
	borderRadius: '20em'
};

const centerStyle = {textAlign: 'center'};
const middleStyle = {verticalAlign: 'middle'};
const dealeroffset = 0;
const userOffset = 0;

class Game extends React.Component {
	constructor(props) {
		super(props);
		this.deck = new Deck.Deck();
		this.deck.shuffle();
		this.state = {
			dealer: {
				hand: [
					{'card': this.deck.popCardFromTop(), 'visibility': 'hide'},
					{'card': this.deck.popCardFromTop(), 'visibility': 'show'},
				]
			},
			user: {
				hand: [
					{'card': this.deck.popCardFromTop(), 'visibility': 'hide'},
					{'card': this.deck.popCardFromTop(), 'visibility': 'show'},
				]
			}
		};
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
		this.props.updateBalance(10).then(this.forceUpdate());
		u = this.props.user;
		console.log('Balance after update: ' + u.balance);
	};

	popCard = () => {
		let c = this.deck.popCardFromTop();
		console.log('Got card: ' + c.toString());
		console.log('New Deck Size: ' + this.deck.size());
		this.forceUpdate();
	};

	render() {
		let { handleSubmit, submitting } = this.props;

		return (
			<div className="container padded">
				<div style={centerStyle}><img src={BlackJackTitle}/></div>

				<div className="container padded rounded-50 border-secondary" style={tableStyle}>
					Inside the table.
					<img src={CardDeck}/>

					{/*Dealer Hand*/}
					<div>
						{_.isDefined(this.state.dealer.hand) &&
						<div>
							{
								this.state.dealer.hand.map((item) =>{
									{this.deck.renderCard(item.card);}
								})
							}
						</div>
						}

					</div>
					{/*Dealer Hand*/}
					<div>

					</div>
				</div>

				<div>Deck Size: {this.deck.size()}</div>
				<div>Full deck of cards...</div>

				<button className={'btn btn-primary'} onClick={this.addToBalance}>Add 10 to Balance</button>
				<br/>
				BALANCE: {this.props.user.balance}
				<br/>
				<button className={'btn btn-success'} onClick={this.popCard}>POP Card</button>
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

