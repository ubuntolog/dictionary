import React from 'react';
import PropTypes from 'prop-types';

class ErrorMessage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div className="jumbotron jumbotron-fluid">
                <div className="container">
                    <h1 className="display-4">{this.props.title}</h1>
                   {this.props.content}
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
