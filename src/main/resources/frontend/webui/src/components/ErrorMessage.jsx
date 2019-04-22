import React from 'react';
import PropTypes from 'prop-types';

class ErrorMessage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div class="jumbotron jumbotron-fluid">
                <div class="container">
                    <h1 class="display-4">{this.props.title}</h1>
                    <p class="lead"> {this.props.content}</p>
                </div>
            </div>
        )
    }
}

ErrorMessage.propTypes = {
    title: PropTypes.string,
    content: PropTypes.object
};

export default ErrorMessage;
