import React from 'react';
import PropTypes from 'prop-types';
// import {Grid, Col, Row, FormGroup, FormControl, Button, Glyphicon, ButtonToolbar, Modal} from 'react-bootstrap';
// import {InputGroup} from 'react-bootstrap/InputGroup';

const formFields = ["name", "email", "phone", "salary", "age", "pets", "tenantsNum", "space", "floor", "roomsNum", "rentPeriod"];
// const BookingsTableRow = ({name, email}) => {
//     return (
//         <Row style={{marginTop:10}}>
//             <Col xs={4} sm={4} md={4} lg={4} >
//                 {name}
//             </Col>
//             <Col xs={4} sm={4} md={4} lg={4} >
//                 {email}
//             </Col>
//         </Row>
//     );
// }
class SearchArea extends React.Component {
    constructor(props) {
        super(props);

        this.handleSelect = this.handleSelect.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);


        this.state = {
            key: 1,
            show: false,
            hasPets: false
        };
    }



    handleSelect(key) {
        this.setState({ key });
        this.props.actions.fetchBookings();
      }

    handleClose() {
        this.setState({ show: false });
    }
    
    handleShow() {
        this.setState({ show: true });
    }




    handleSubmit(event) {
        console.log("Submit");
        event.preventDefault();
        const data = new FormData(event.target);

        // for (let i = 0; i<formFields.length; i++) {
            
        //     this.handleValidationWithNameAndValue(formFields[i], "");
          
        // }
        console.log(data.get("query"));
        this.props.actions.submitRegistration(
                                                data.get(formFields[0])                                               
                                            );
        this.props.actions.fetchBookings();
    }

    // handleFieldValidation(e) {
    //     this.handleValidationWithNameAndValue(e.target.name, e.target.value);
    // }

    handleValidationWithNameAndValue(name, value) {
        console.log(name);
        console.log(value);
        let validation = {};
        validation[name] = {};
        validation[name]["state"] = null;
        validation[name]["message"] = "";
        
        if (name == formFields[0]) {
            if (value.length == 0) {
                validation[name]["state"] = "error";
                validation[name]["message"] = "Name cannot be empty";
            } else {
                validation[name]["state"] = "success";
            }            
        }

       

        this.props.actions.validateForm(validation);
    }

    componentDidMount() {
        //this.props.actions.fetchBookings();
    }

    render() {
        // let validation = (this.props.validation ? this.props.validation : {});
      
        // let validationStatus = {};
        // for (let field of formFields) {
        //     if (!(field in validation)) {
        //         validationStatus[field] = {};
        //         validationStatus[field]["state"] = null;
        //         validationStatus[field]["message"] = "";
        //     } else {
        //         validationStatus[field] = validation[field];
        //     }
        // }
        // console.log(validationStatus);
        return(
          
            <form onSubmit={this.handleSubmit.bind(this)}>
            <div className="form-group row" >
                <div className="col-2">
                
                </div>
                <div className="col-8">
                    
                        <input className="form-control" name="query" type="text" placeholder="Enter a word" />
                  
                </div>
                <div className="col-2">
                
                </div>
            </div>
                 

            </form>                 
                   
           
        )
    }
}

SearchArea.propTypes = {
    apiinfo: PropTypes.object.isRequired,
    validation: PropTypes.object.isRequired,
    bookings: PropTypes.array.isRequired
};

export default SearchArea;
