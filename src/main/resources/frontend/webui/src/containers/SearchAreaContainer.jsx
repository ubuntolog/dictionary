import {bindActionCreators} from 'redux'
import {connect} from 'react-redux';
import SearchArea from '../components/SearchArea';
import * as actions from '../actions/actions';

const mapStateToProps = (state) => {
    return {
        apiinfo: state.apiinfo,
        bookings: state.bookings,
        validation: state.validation
    };
};

const mapDispatchToProps = (dispatch) => {
    return {
        actions: bindActionCreators(actions, dispatch)
    };
};

export const SearchAreaContainer = connect(mapStateToProps, mapDispatchToProps)(SearchArea);
