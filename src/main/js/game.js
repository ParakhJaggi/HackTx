import _ from 'lodash';

import React from 'react';
import {Link} from 'react-router-dom';
import {connect} from 'react-redux';
import * as Users from 'js/users';
import * as Deck from 'js/decks';
import * as Table from 'js/table';
import {Rank, Suit} from 'js/cards';
import Images from 'js/images';
import BlackJackTitle from '../resources/images/blackjack.png';
import CardDeck from '../resources/images/blackjack.png';

const ImageMap = [Images.AceClubs, Images.TwoClubs, Images.ThreeClubs, Images.FourClubs, Images.FiveClubs, Images.SixClubs, Images.SevenClubs, Images.EightClubs, Images.NineClubs, Images.TenClubs, Images.JackClubs, Images.QueenClubs, Images.KingClubs, Images.AceHearts, Images.TwoHearts, Images.ThreeHearts, Images.FourHearts, Images.FiveHearts, Images.SixHearts, Images.SevenHearts, Images.EightHearts, Images.NineHearts, Images.TenHearts, Images.JackHearts, Images.QueenHearts, Images.KingHearts, Images.AceDiamonds, Images.TwoDiamonds, Images.ThreeDiamonds, Images.FourDiamonds, Images.FiveDiamonds, Images.SixDiamonds, Images.SevenDiamonds, Images.EightDiamonds, Images.NineDiamonds, Images.TenDiamonds, Images.JackDiamonds, Images.QueenDiamonds, Images.KingDiamonds, Images.AceSpades, Images.TwoSpades, Images.ThreeSpades, Images.FourSpades, Images.FiveSpades, Images.SixSpades, Images.SevenSpades, Images.EightSpades, Images.NineSpades, Images.TenSpades, Images.JackSpades, Images.QueenSpades, Images.KingSpades];

const centerStyle = {textAlign: 'center'};

class Game extends React.Component {
	constructor() {
		super();
		this.deck = new Deck.Deck();
	}

	shuffleDeck = () => {
		console.log('Shuffling deck...');
		this.deck.shuffle();
		this.forceUpdate();
	};

	render() {
		return (
			<div className="container padded">
				<div style={centerStyle}><img src={BlackJackTitle}/></div>

				<Table.Table/>

				<div>Deck Size: {this.deck.size()}</div>
				<div>Full deck of cards...</div>

				<button className={'btn btn-success'} onClick={this.shuffleDeck}>Shuffle Cards</button>

				{this.deck.showDeckImages()}
			</div>
		);
	}
}

Game = connect(
	state => ({}),
	dispatch => ({
		register: user => dispatch(Users.Actions.register(user))
	})
)(Game);

export {Game};

