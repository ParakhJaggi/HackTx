import * as Cards from 'js/cards';
import React from 'react';
import Images from 'js/images';
const ImageMap = [Images.AceClubs, Images.TwoClubs, Images.ThreeClubs, Images.FourClubs, Images.FiveClubs, Images.SixClubs, Images.SevenClubs, Images.EightClubs, Images.NineClubs, Images.TenClubs, Images.JackClubs, Images.QueenClubs, Images.KingClubs, Images.AceHearts, Images.TwoHearts, Images.ThreeHearts, Images.FourHearts, Images.FiveHearts, Images.SixHearts, Images.SevenHearts, Images.EightHearts, Images.NineHearts, Images.TenHearts, Images.JackHearts, Images.QueenHearts, Images.KingHearts, Images.AceDiamonds, Images.TwoDiamonds, Images.ThreeDiamonds, Images.FourDiamonds, Images.FiveDiamonds, Images.SixDiamonds, Images.SevenDiamonds, Images.EightDiamonds, Images.NineDiamonds, Images.TenDiamonds, Images.JackDiamonds, Images.QueenDiamonds, Images.KingDiamonds, Images.AceSpades, Images.TwoSpades, Images.ThreeSpades, Images.FourSpades, Images.FiveSpades, Images.SixSpades, Images.SevenSpades, Images.EightSpades, Images.NineSpades, Images.TenSpades, Images.JackSpades, Images.QueenSpades, Images.KingSpades];

let Rank = Cards.Rank;
let Suit = Cards.Suit;
let card = Cards.Card;

export class Deck {
    constructor() {
        this.deck = [];
        this.imageIndex = [];

		for (let i in Suit) {
			for (let j in Rank) {
				let imageIndex = i+j;
				this.deck.push(new card(Rank[j], Suit[i]));
			}
		}
    }

    size = () => {
        return this.deck.length;
    };
    
    contains = (card) => {
        return this.deck.indexOf(card) >= 0;
    };

    shuffle = () => {
		for (let i = this.deck.length - 1; i > 0; i--) {
			const j = Math.floor(Math.random() * (i + 1));
			[this.deck[i], this.deck[j]] = [this.deck[j], this.deck[i]];
		}
    };

	showDeckImages = () => {
		let images = [];
		for (let i in ImageMap) {
			images.push(<img src={ImageMap[i]} key={ImageMap[i] + '-' + i} />);
		}
		return images;
	};
}