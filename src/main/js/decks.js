import * as Cards from 'js/cards';
import React from 'react';
import Images from 'js/images';
import {RankToWord} from 'js/cards';
import {SuitToWord} from 'js/cards';
import {Card} from 'js/cards';

export const ImageMap = [Images.AceClubs, Images.TwoClubs, Images.ThreeClubs, Images.FourClubs, Images.FiveClubs, Images.SixClubs, Images.SevenClubs, Images.EightClubs, Images.NineClubs, Images.TenClubs, Images.JackClubs, Images.QueenClubs, Images.KingClubs, Images.AceHearts, Images.TwoHearts, Images.ThreeHearts, Images.FourHearts, Images.FiveHearts, Images.SixHearts, Images.SevenHearts, Images.EightHearts, Images.NineHearts, Images.TenHearts, Images.JackHearts, Images.QueenHearts, Images.KingHearts, Images.AceDiamonds, Images.TwoDiamonds, Images.ThreeDiamonds, Images.FourDiamonds, Images.FiveDiamonds, Images.SixDiamonds, Images.SevenDiamonds, Images.EightDiamonds, Images.NineDiamonds, Images.TenDiamonds, Images.JackDiamonds, Images.QueenDiamonds, Images.KingDiamonds, Images.AceSpades, Images.TwoSpades, Images.ThreeSpades, Images.FourSpades, Images.FiveSpades, Images.SixSpades, Images.SevenSpades, Images.EightSpades, Images.NineSpades, Images.TenSpades, Images.JackSpades, Images.QueenSpades, Images.KingSpades];

export class Deck {
    constructor() {
        this.deck = [];

		console.log('Constructing card deck...');

		for(let suitIterator = 1; suitIterator < SuitToWord.length; suitIterator++){
			for(let rankIterator = 1; rankIterator < RankToWord.length; rankIterator++){
				let imageIndex = ((suitIterator-1)*13)+rankIterator-1;
				this.deck.push(new Card(rankIterator, suitIterator, imageIndex));
			}
		}
		// let iIndex = 0;
		// for (let i in Suit) {
		// 	let jIndex = 0;
		// 	for (let j in Rank) {
		// 		let imageIndex = 0;
		// 		if(iIndex === 0){
		// 			if(jIndex === 0){
		// 				imageIndex = 0;
		// 			} else {
		// 				imageIndex = jIndex;
		// 			}
		// 		} else {
		// 			if(jIndex === 0){
		// 				imageIndex = iIndex * 13;
		// 			} else {
		// 				imageIndex = (iIndex * 13) + jIndex;
		// 			}
		// 		}
		//
		// 		console.log('Pushing card with rank: ' + j + ' and jIndex: ' + jIndex + ' and image_index: ' + imageIndex);
		// 		this.deck.push(new card(Rank[j], Suit[i], imageIndex));
		// 		jIndex++;
		// 	}
		// 	iIndex++;
		// }

		console.log(this.deck);
    }

	size = () => {
		return this.deck.length;
	};

	contains = (card) => {
		return this.deck.indexOf(card) >= 0;
	};

	popCardFromTop = () => {
		let card = this.deck[0];
		this.deck.splice(0, 1);
		return card;
	};

	shuffle = () => {
		for (let i = this.deck.length - 1; i > 0; i--) {
			const j = Math.floor(Math.random() * (i + 1));
			[this.deck[i], this.deck[j]] = [this.deck[j], this.deck[i]];
		}
	};

	showDeckImages = () => {
		let images = [];
		for (let card in this.deck) {

			images.push(<img src={ImageMap[this.deck[card].image_index]} key={ImageMap[this.deck[card].image_index] + '-' + card} />);
		}
		return images;
	};
}