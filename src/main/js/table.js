import React from 'react';
import cloth from '../resources/images/poker_cloth.jpg';

const tableStyle = {
	width: 1000,
	height: 750,
	backgroundRepeat: true,
	backgroundImage: `url(${cloth})`,
	borderRadius: '20em'
};

class Table extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		return (
			<div className="container padded rounded-50 border-secondary" style={tableStyle}>
				Inside the table.
			</div>
		);
	}
}

export {Table};