/**
 * 
 */
$("#save").on(
		"click",
		function() {

			projectName = $("#projectName").val();
			encodedProjectName = encodeURIComponent(projectName);

			url = $("#url").val();
			encodedUrl = encodeURIComponent(url);

			requestCount = $("#requests").val();
			encodedRequests = encodeURIComponent(requestCount);

			$("#status-msg").text("Saving project '" + projectName + "'...");

			$.ajax({
				type : "GET",
				url : "/client/v1/project/create?uri=" + encodedUrl + "&count="
						+ encodedRequests + "&projectname="
						+ encodedProjectName,
				success : function(data) {
					$("#status-msg").text("Project saved.");

					setTimeout(function() {
						$("#status-msg").empty();
					}, 3000);
				},
				error : function(jqXHR, status) {
					$("#status-msg").text("Error: unable to save project.");
					setTimeout(function() {
						$("#status-msg").empty();
					}, 3000);
				}
			});

		});

function startLoadTest() {

}

function startProgress() {

	/*
	 * max: tells the max value of the progress bar
	 */
	var progressbar = $("#progressbar-2");

	// progressbar() is a jQuery function built into the jquery progressbar
	// widget (part of jQuery UI library).
	$("#progressbar-2").progressbar({
		max : 100
	// max is a option that indicates the max value before the progress bar is
	// filled is "100"
	});

	function progress() {
		var val = progressbar.progressbar("value") || 0;

		// set the "value" of the progress bar to whatever the "val" variable
		// is.
		// "value" indicates the progress on the progress bar
		progressbar.progressbar("value", val + 1);

		// call progress(), as long as val < 99 (not 100%)
		if (val < 99) {
			setTimeout(progress, 100); // progress is called every 100
										// milliseconds, or .1 seconds
		} else {
			$("#start").attr("value", "Start"); // change the "Stop" button to a
												// "Start" button since the
												// operation is finished
			$("#status-msg").text("Requests sent!");

			setTimeout(function() {
				$("#status-msg").empty();
			}, 3000);
			$("#start").trigger("click");
			$(".active").removeClass("active in");
			$("#saved-projects").addClass("active in");
		}
	}

	// setTimeOut automatically starts filling the progress bar, after 1000
	// milliseconds, or 1 second.
	setTimeout(progress, 1000);
}

// resets the progress bar
function stopProgress() {
	$("#progressbar-2").progressbar("destroy");
}

function handler1() {
	$(this).attr("value", "Stop");
	startProgress(); // start filling up the progress bar
	$(this).one("click", handler2);
}

function handler2() {
	$(this).attr("value", "Start");
	stopProgress();
	$(this).one("click", handler1);
}

$("#start").one("click", handler1);

(function() {
	$("#chart-container").insertFusionCharts({
		type : 'msarea',
		renderAt : 'chart-container',
		width : '100%',
		height : '600',
		dataFormat : 'json',
		dataSource : {
			"chart" : {
				"caption" : "Response Times",
				"subCaption" : "Response Time VS Test Duration",
				"xAxisName" : "Total Test Duration (s)",
				"yAxisName" : "Average Response Time (ms)",
				"numberPrefix" : "",
				"paletteColors" : "#0075c2,#1aaf5d",
				"bgColor" : "#ffffff",
				"showBorder" : "0",
				"showCanvasBorder" : "0",
				"plotBorderAlpha" : "10",
				"usePlotGradientColor" : "0",
				"legendBorderAlpha" : "0",
				"legendShadow" : "0",
				"plotFillAlpha" : "60",
				"showXAxisLine" : "1",
				"axisLineAlpha" : "25",
				"showValues" : "0",
				"captionFontSize" : "16",
				"subcaptionFontSize" : "16",
				"subcaptionFontBold" : "0",
				"divlineColor" : "#999999",
				"divLineIsDashed" : "1",
				"divLineDashLen" : "1",
				"divLineGapLen" : "1",
				"showAlternateHGridColor" : "0",
				"toolTipColor" : "#ffffff",
				"toolTipBorderThickness" : "0",
				"toolTipBgColor" : "#000000",
				"toolTipBgAlpha" : "80",
				"toolTipBorderRadius" : "2",
				"toolTipPadding" : "5",
			},

			"categories" : [ {
				"category" : [ {
					"label" : "0"
				}, {
					"label" : "25"
				}, {
					"label" : "50"
				}, {
					"label" : "75"
				}, {
					"label" : "100"
				}, {
					"label" : "125"
				}, {
					"label" : "150"
				} ]
			} ],

			"dataset" : [ {
				"seriesname" : "Amazon",
				"data" : [ {
					"label" : "1",
					"value" : "10000"
				}, {
					"label" : "2",
					"value" : "14500"
				}, {
					"label" : "5",
					"value" : "13500"
				}, {
					"label" : "5",
					"value" : "15000"
				}, {
					"value" : "1500"
				}, {
					"label" : "10",
					"value" : "17650"
				}, {
					"label" : "100",
					"value" : "19500"
				} ]
			}, {
				"seriesname" : "Google",
				"data" : [ {
					"value" : "8400"
				}, {
					"value" : "9800"
				}, {
					"value" : "11800"
				}, {
					"value" : "14400"
				}, {
					"value" : "18800"
				}, {
					"value" : "24800"
				}, {
					"value" : "30800"
				} ]
			}, {
				"seriesname" : "Netflix",
				"data" : [ {
					"value" : "1000"
				}, {
					"value" : "2000"
				}, {
					"value" : "10000"
				}, {
					"value" : "14400"
				}, {
					"value" : "18800"
				}, {
					"value" : "24800"
				}, {
					"value" : "30800"
				} ]
			} ]
		}
	});
}());