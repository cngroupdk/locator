// tutorial8.js

var CommentBox = React.createClass({

    loadCommentsFromServer: function() {
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            cache: false,
            success: function(data) {
                this.setState({data: data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },

    getInitialState: function() {
        return {data: []};
    },

    componentDidMount: function() {
        this.loadCommentsFromServer();
        //setInterval(this.loadCommentsFromServer, this.props.pollInterval);
    },

    render: function() {
        return (
            <div className="commentBox">
                <h1>Buildings</h1>
                <CommentList data={this.state.data}/>
            </div>
        );
    }
});

var CommentList = React.createClass({
    render: function() {

        var commentNodes = this.props.data.map(
            function (comment) {
                return (
                  <Comment commentData={comment} key={comment.buildingId}/>
                );
            }
        );

        return (
            <div className="commentList">
                {commentNodes}
            </div>
        );
    }
});

var Comment = React.createClass({

    rawMarkup: function() {
        var md = new Remarkable();
        var rawMarkup = md.render(this.props.commentData.children.toString());
        return { __html: rawMarkup };
    },

    render: function() {
        return (
            <div className="comment">
                <h2 className="commentAuthor">
                    {this.props.commentData.name}
                </h2>
                <p>
                    {this.props.commentData.city}
                </p>
                <p>
                    {this.props.commentData.postalCode}
                </p>
                <p>
                    {this.props.commentData.streetName}
                </p>
                <p>
                    {this.props.commentData.type}
                </p>
            </div>

        );
    }
});

var CommentForm = React.createClass({
    render: function() {
        return (
            <div className="commentForm">
                Hello, world! I am a CommentForm.
            </div>
        );
    }
});

ReactDOM.render(
    <CommentBox url="/buildings"/>,
    document.getElementById('content')
);