import React from 'react';
import "babel-polyfill";
import {Grid, Row, Col} from 'react-bootstrap';


class About extends React.Component {
    constructor(props) {
        super(props);

    }

    componentDidMount() {

    }

    render() {
        return(
            <div>
                <Row>
                    <Col>
                        <Grid className="container-100-grid">
                            <Row>
                                <Col>
                                   // {this.state.lazyLoadedTutorial}
                                   About
                                </Col>
                          </Row>
                      </Grid>
                  </Col>
              </Row>
          </div>
        )
    }
}

About.propTypes = {};

export default About;
