import _ from 'lodash';

import React from 'react';
import {Link} from 'react-router-dom';
import {connect} from 'react-redux';
import * as Bessemer from 'js/alloy/bessemer/components';
import * as Users from 'js/users';
import * as Deck from 'js/decks';
import {Rank, Suit} from 'js/cards';
import BlackJackTitle from '../resources/images/blackjack.png';
import CardDeck from '../resources/images/deck_small.png';
import CardBack from '../resources/images/hit_small.png';
import * as ReduxForm from 'redux-form';
import cloth from '../resources/images/poker_cloth.jpg';
import garlicGambler from '../resources/images/garlicGambler.png';
import {ImageMap} from 'js/decks';

const tableStyle = {
	width: 1000,
	height: 750,
	backgroundRepeat: true,
	backgroundImage: `url(${cloth})`,
	borderRadius: '20em',
	textAlign: 'center',
	margin: 'auto',
};

const garlicStyle = {
	width: 200,
	height: 200,
	margin: 'auto',
	textAlign: 'center',
	paddingRight: 0,
	borderRight: 0
};

const rowC = {
	display: 'flex'
};

const centerStyle = {textAlign: 'center', width: '100%', paddingLeft: 0, borderLeft: 0};
const middleStyle = {verticalAlign: 'middle', height: '100%'};
const dealeroffset = 0;
const userOffset = 0;
var playerValue = 0;
var dealerValue	= 0;
const DECK_SIZE = 52;

function getRandomInt(max) {
    return Math.floor(Math.random() * Math.floor(max));
}

class Game extends React.Component {

	constructor(props) {
		super(props);
		this.deck = new Deck.Deck();
		this.deck.shuffle();
		this.state = {
			dealer: {
				hand: [
					{'card': this.deck.popCardFromTop(), 'visibility': false},
					{'card': this.deck.popCardFromTop(), 'visibility': true},
				],
				done: false
			},
			user: {
				hand: [
					{'card': this.deck.popCardFromTop(), 'visibility': true},
					{'card': this.deck.popCardFromTop(), 'visibility': true},
				],
				done: false
			},
			bet: 10
		};
		this.state.result = 'incomplete';
	}

	shuffleDeck = () => {
		console.log('Shuffling deck... ' + this.deck === null);
		this.deck.shuffle();
		this.forceUpdate();
	};

	restart = () =>  {
		console.log('Restarting game');
		playerValue = 0;
		dealerValue = 0;
		this.deck = new Deck.Deck();
		this.deck.shuffle();
		this.state = {
			dealer: {
				hand: [
					{'card': this.deck.popCardFromTop(), 'visibility': false},
					{'card': this.deck.popCardFromTop(), 'visibility': true},
				],
				done: false
			},
			user: {
				hand: [
					{'card': this.deck.popCardFromTop(), 'visibility': true},
					{'card': this.deck.popCardFromTop(), 'visibility': true},
				],
				done: false
			}
		};
		this.state.result = 'incomplete';
		this.forceUpdate();
	};

	getValue = (player) => {
		let total = 0;
		let numAces = 0;
		console.log('Player hand length: ' + player.hand.length);
		for(let c = 0; c < player.hand.length; c++)	{
			console.log(Object.values(player.hand[c]));
			switch(player.hand[c].card.rank) {
				// Face cards
				case 11:
				case 12:
				case 13:
					total += 10;
					break;
				// Ace
				case 1:
					numAces++;
					break;
				// Everything else
				default:
					total += player.hand[c].card.rank;
			}
		}
		while(numAces-- !== 0){
			if(total + 11 + numAces <= 21){
				total += 11;
			} else total++;
		}

		return total;
	};

	getUserValue = () => {
		return this.getValue(this.state.user);
	};
	getDealerValue = () =>{
		return this.getValue(this.state.dealer);
	};

	dealerMove = () => {
		if(this.state.user.done){
			while(this.getDealerValue() <= 17){
				this.state.dealer.hand.push({'card': this.deck.popCardFromTop(), 'visibility': true});
			}
			this.state.dealer.done = true;
		} else {
			if(this.getDealerValue() <= 17){
				this.state.dealer.hand.push({'card': this.deck.popCardFromTop(), 'visibility': true});
			}
		}

		this.getWinner();
		this.forceUpdate();
	};

	userHit = () =>  {
		this.state.user.hand.push({'card': this.deck.popCardFromTop(), 'visibility': true});
		this.forceUpdate();
	};

	userStay = () =>  {
		this.state.user.done = true;
		this.dealerMove();
	};

	getWinner = () => {
		if(this.state.user.done && this.state.dealer.done) {
			if(this.getUserValue() > this.getDealerValue()){
				this.state.result = 'win';
				this.addToBalance(this.state.bet*2);
			} else if(this.getUserValue() < this.getDealerValue()){
				this.state.result = 'lose';
				this.addToBalance(-this.state.bet);
			} else {
				this.state.result = 'draw';
				this.addToBalance(this.state.bet);
			}
			this.forceUpdate();
			setTimeout(this.restart(), 5000);
		}
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
		return (
			<div className="container padded">
				<div style = {rowC}>
					<div><img src={garlicGambler} style={garlicStyle}/></div>
					<div style={centerStyle}><img src={BlackJackTitle}/></div>
				</div>
				<div className="container padded rounded-50 border-secondary" style={tableStyle}>
					<br/><br/><br/><br/>
					{this.state.result === 'win' && <div>YOU WIN!!!</div>}
					{this.state.result === 'lose' && <div>YOU LOST</div>}
					{this.state.result === 'draw' && <div>DRAW</div>}
					<h1 style={{fontSize:'300%',color:'white',fontFamily:'Charmonman'}}>PLAYER: {this.getUserValue()}</h1>

					<br/>
					<br/><br/>
					{/*Dealer Hand*/}
					{_.isDefined(this.state.dealer.hand) &&
					<div className={'row align-items-center d-inline'} style={middleStyle}>
						{this.state.dealer.hand.map(item => (
								<img src={item.visibility ? ImageMap[item.card.image_index] : CardBack} alt={item.card.getCardName()} key={'img' + item.card.getCardName()}/>
							)
						)}
					</div>
					}
					<br/><br/>

					<div className={'row align-items-center'}>
						<img src={CardDeck}/>
					</div>

					<br/><br/>
					{/*User Hand*/}
					{_.isDefined(this.state.user.hand) &&
					<div className={'row align-items-center d-inline'} style={middleStyle}>
						{this.state.user.hand.map(item => (
								<img src={item.visibility ? ImageMap[item.card.image_index] : CardBack} alt={item.card.getCardName()} key={'img' + item.card.getCardName()}/>
							)
						)}
					</div>
					}
				</div>

				<button className={'btn btn-primary'} onClick={this.addToBalance}>Add 10 to Balance</button>
				<br/>
				BALANCE: {this.props.user.balance}
				<br/>
				<br/>
				<button className={'btn btn-success'} onClick={this.popCard}>POP Card</button>
				<br/>

				<button className={'btn btn-success'} onClick={this.shuffleDeck}>Shuffle Cards</button>
				<button className={'btn btn-secondary'} onClick={this.userHit}>Hit</button>
				<button className={'btn btn-secondary'} onClick={this.userStay}>Stay</button>
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

