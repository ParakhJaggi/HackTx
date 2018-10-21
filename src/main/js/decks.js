
import * as Cards from 'js/cards';
import React from 'react';
let Rank = Cards.Rank;
let Suit = Cards.Suit;
let card = Cards.Card;

export class Deck {
    constructor() {
        this.deck = [];

		for (let n1 in Suit) {
			let s = Suit[n1];
			for (let n2 in Rank) {
				let r = Rank[n2];
				this.deck.push(new card(r, s));
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

    printCards = () => {
        let result = '';
        if(this.deck !== null && this.deck.length > 0) {
			for (let i = 0; i < this.deck.length; i++) {
				console.log(this.deck[i]);
				//result =  result + this.deck[i].rank + ' ' + this.deck[i].suit + ', ';
				result = result + this.deck[i].getCardPath() + '\r\n';
			}
			return result;// return Object.values(this.deck)
		}
    };

	printCard = () => {
		if(this.deck !== null && this.deck.length > 0) {
			return this.deck[0].getCardPath();
		}
	};
}