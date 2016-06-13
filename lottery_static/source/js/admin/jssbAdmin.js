/*
 * 更行右边当天历史开奖信息
 */
function updatejsRightStat() {

	var strUrl = context + '/index.php?s=/home/result/getLMCLList/';
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({
		url : queryUrl,
		data : {t : 'jsks'},
		async : false,
		dataType : "json",
		type : "POST",
		success : function(json) {
			if (json && json.data) {
				drawRightPanelTable(json.data);
			}

		}
	});
}

function drawRightPanelTable(queryList) {
	var html = [], h = -1;
	html[++h] = '<table cellspacing="0" cellpadding="0" border="0" width="100%" class="king changlong" >';
	html[++h] = '<tbody>';
	html[++h] = '<tr><td colspan="6" class="tt">近期开奖结果</td></tr>';
	if (queryList) {
		$.each(queryList, function(i, n) {
			html[++h] = '<tr>'
			html[++h] = '<td width="36" class="even">' + i + '期</td>';
			for ( var b in n.ball) {
				html[++h] = "<td width='27'><img src='/images/" + '4_'+ n.ball[b] + ".gif' /></td>";
			}

			html[++h] = '<td width="27">' + n.sum + '</td>';
			//如果围骰
			if (n.daxiao == '通吃') {
				html[++h] = '<td width="27" class="green">通吃</td>';
			} else if (n.daxiao == '小') {//小
				html[++h] = '<td width="27">小</td>';
			} else if (n.daxiao == '大') {//大
				html[++h] = '<td width="27" class="red">大</td>';
			}
			html[++h] = '</tr>';

		});
	}
	html[++h] = '</tbody>';
	html[++h] = '</table>';
	$('#jsright')[0].innerHTML = html.join('');
}