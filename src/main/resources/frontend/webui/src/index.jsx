import React from 'react';
import PropTypes from 'prop-types';
import {render} from 'react-dom';
import {Provider, connect} from 'react-redux';
import {Router, IndexRoute, Route, browserHistory, hashHistory, Link } from 'react-router';
import {syncHistoryWithStore, routerMiddleware} from 'react-router-redux';
import {compose, applyMiddleware, createStore} from 'redux';
import thunkMiddleware from 'redux-thunk';
import Alert from 'react-s-alert';

require('react-s-alert/dist/s-alert-default.css');
require('react-s-alert/dist/s-alert-css-effects/slide.css');

import rootReducers from './actions/reducers';
import {fetchApiInfo} from './actions/actions';

// import {Grid, Row, Col, Button, Navbar, Well, Image} from 'react-bootstrap';

import {SearchAreaContainer} from './containers/SearchAreaContainer';
import ErrorMessage from './components/ErrorMessage';
import About from './components/About';

require('../app/css/app.css');


const devTools = (window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__())
    || (f => f);

const middleware = compose(
    applyMiddleware(
        thunkMiddleware,
        routerMiddleware(browserHistory),
        // createLogger(),
    ),
    devTools
);
const store = createStore(rootReducers, middleware);
const history = syncHistoryWithStore(browserHistory, store);


class Frame extends React.Component {
    constructor(props) {
        super(props);
    }

    inIframe () {
        try {
            return window.self !== window.top;
        } catch (e) {
            return true;
        }
    }

    render() {
        return (
            <div>
                <div className="card">
                    <div className="card-header">
                        <h2>Dictionary Application</h2>
                    </div>
                    <div className="card-body">
                        <blockquote className="blockquote mb-0">
                        {this.props.children}
                        <footer className="blockquote-footer">Someone famous in <cite title="Source Title">Source Title</cite></footer>
                        </blockquote>
                    </div>
                </div>
            </div>
       )
    }
}
Frame = connect(s => s)(Frame);

class App extends React.Component {
    constructor(props) {
        super(props);
        store.dispatch(fetchApiInfo());
    }

    render() {
        return (
            <Provider store={store}>
                <div>
                    <Router history={history}>
                        <Route path={window.APP_CONTEXT_PATH+'/'} component={Frame}>
                            <IndexRoute component={SearchAreaContainer}/>
                            <Route path={window.APP_CONTEXT_PATH+'/about'} component={About} />
                            <Route path='*' component={NotFound} />
                        </Route>
                    </Router>

                    <Alert stack={{limit:5}}/>
                </div>
            </Provider>
        );
    }
}

const NotFound = () => (
    <div>
        <ErrorMessage title={"Page not found"} content={<p>The requested page does not exist. Please check the URL or try to start from the <a href={window.APP_CONTEXT_PATH+"/"}>main page</a></p>} />
    </div>
);

render(<App />, document.getElementById('react') );
