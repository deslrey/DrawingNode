<template>
    <div id="app">
        <div class="content-header">
            <h1>文件管理</h1>
        </div>
        <div class="app-container">
            <div class="box">
                <div class="filter-container">
                    <el-input placeholder="文件名称" v-model="pagination.fileName" style="width: 200px;"
                        class="filter-item"></el-input>
                    <el-button type="primary" @click="getAll()" class="dalfBut">查询</el-button>
                    <el-button type="warning" class="butT" @click="handleCreate()">新增</el-button>
                </div>

                <el-table size="small" current-row-key="id" :data="dataList" stripe highlight-current-row>
                    <el-table-column type="index" align="center" label="序号"></el-table-column>
                    <el-table-column prop="fileName" label="文件名称" align="center"></el-table-column>
                    <el-table-column prop="createTime" label="创建时间" align="center">
                        <template slot-scope="scope">
                            {{ formatDate(scope.row.createTime) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center">
                        <template slot-scope="scope">
                            <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
                            <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <!--分页组件-->
                <div class="pagination-container">
                    <el-pagination class="pagination" @current-change="handleCurrentChange"
                        :current-page="pagination.currentPage" :page-size="pagination.pageSize"
                        layout="total, prev, pager, next, jumper" :total="pagination.total">
                    </el-pagination>
                </div>

                <!-- 新增标签弹层 -->
                <div class="add-form">
                    <el-dialog title="新增文件" :visible.sync="dialogFormVisible">
                        <el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right"
                            label-width="100px">
                            <el-row>
                                <el-col :span="12">
                                    <el-form-item label="文件名称" prop="fileName">
                                        <el-input v-model="importForm.fileName" placeholder="不输入名称采用默认文件名" />
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="24">
                                    <el-form-item label="上传文件:" prop="excel">
                                        <el-upload class="upload-demo" ref="upload" action :http-request="httpRequest"
                                            :before-upload="beforeUpload" :on-exceed="handleExceed" :limit="1">
                                            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                                            <div slot="tip" class="el-upload__tip">只能上传.xlsx文件，且不超过5M</div>
                                        </el-upload>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button @click="cancel()">取消</el-button>
                            <el-button type="primary" @click="submitImportForm">开始导入</el-button>
                        </div>
                    </el-dialog>
                </div>

                <!-- 编辑标签弹层 -->
                <div class="add-form">
                    <el-dialog title="编辑检查项" :visible.sync="dialogFormVisible4Edit">
                        <el-form ref="dataEditForm" :model="formData" :rules="rules" label-position="right"
                            label-width="100px">
                            <el-row>
                                <el-col :span="12">
                                    <el-form-item label="文件" prop="fileName">
                                        <el-input v-model="formData.fileName" />
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button @click="cancel()">取消</el-button>
                            <el-button type="primary" @click="handleEdit()">确定</el-button>
                        </div>
                    </el-dialog>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import batchFiles from '@/api/file/batchFiles';
import moment from 'moment'


export default {
    name: 'BatchFiles',
    data() {
        return {
            dataList: [], //当前页要展示的列表数据
            dialogFormVisible: false, //添加表单是否可见
            dialogFormVisible4Edit: false, //编辑表单是否可见
            formData: {}, //表单数据
            rules: { //校验规则
                fileName: [{ required: false, message: '文件名称为必填项', trigger: 'blur' }]
            },
            pagination: { //分页相关模型数据
                currentPage: 1, //当前页码
                pageSize: 10, //每页显示的记录数
                total: 0, //总记录数
                fileName: "",
            },
            //导入表单数据
            importForm: {
                fileName: ''
            },
            //存放上传文件
            fileList: []
        };
    },
    created() {
        //调用查询全部数据的操作
        this.getAll();
    },
    methods: {
        getAll() {

            const params = {
                fileName: this.pagination.fileName,
                currentPage: this.pagination.currentPage,
                pageSize: this.pagination.pageSize
            };
            console.log('params ------> ', params);
            batchFiles.getAll(params).then(response => {

                console.log('getAll ------> ', response);

                this.pagination.pageSize = response.data.size;
                this.pagination.currentPage = response.data.totalPages;
                this.pagination.total = response.data.totalElements;
                this.dataList = response.data.content;
            })
        },
        handleCurrentChange(currentPage) {
            this.pagination.currentPage = currentPage;
            this.getAll();
        },
        handleCreate() {
            this.dialogFormVisible = true;
            this.resetForm();
        },
        resetForm() {
            this.formData = {};
        },
        httpRequest(option) {
            this.fileList.push(option)
        },
        formatDate(date) {
            return moment(date).format('YYYY-MM-DD HH:mm:ss');
        },

        // 上传前处理
        beforeUpload(file) {
            let fileSize = file.size
            const FIVE_M = 500 * 1024 * 1024;
            //大于5M，不允许上传
            if (fileSize > FIVE_M) {
                this.$message.error("最大上传500M")
                return false
            }
            return true
        },
        // 文件数量过多时提醒
        handleExceed() {
            this.$message({ type: 'error', message: '最多支持1个附件上传' })
        },
        //导入Excel病种信息数据
        submitImportForm() {
            // 使用form表单的数据格式
            const params = new FormData()
            // 将上传文件数组依次添加到参数paramsData中
            this.fileList.forEach((x) => {
                params.append('file', x.file)
            });
            // 将输入表单数据添加到params表单中
            params.append('fileName', this.importForm.fileName)
            //这里根据自己封装的axios来进行调用后端接口
            console.log('params -----> ', params);
            batchFiles.submitImportForm(params).then(response => {
                console.log('submitImportForm -----> ', response);
                // this.$refs.importFormRef.resetFields()//清除表单信息
                // this.$refs.upload.clearFiles()//清空上传列表
                this.fileList = []//集合清空
                this.dialogFormVisible = false//关闭对话框
                console.log('submitImportForm -----> ', response);
                this.$message({
                    message: response.message,
                    type: 'success'
                })
                this.getAll()
            })
        },

        cancel() {
            this.dialogFormVisible = false;
            this.dialogFormVisible4Edit = false;
            this.$message.info("当前操作取消!!!");
        },
        handleDelete(row) {
            console.log('row1 ------ > ', row);
            this.$confirm("此操作永久删除当前信息，是否继续？", "提示", { type: "info" }).then(() => {
                batchFiles.handleDelete(row).then(response => {
                    console.log('handleDelete ------ > ', response);
                }).catch(error => {
                    console.log('error ------ > ', error);
                }).finally(() => {
                    this.getAll();
                });

            }).catch(() => {
                this.$message.info("取消操作!!!");
            });

        },

        // axios.delete(`/books/${row.id}`).then(res => {
        //     if (res.data.flag) {
        //         this.$message.success("删除成功!!!");
        //     } else {
        //         this.$message.error("数据同步失败，自动刷新!!!");
        //     }
        // }).finally(() => {
        //     this.getAll();
        // });
        handleUpdate(row) {
            axios.get(`/books/${row.id}`).then(res => {
                if (res.data.flag && res.data.data != null) {
                    this.dialogFormVisible4Edit = true;
                    this.formData = res.data.data;
                } else {
                    this.$message.error("数据同步失败，自动刷新!!!");
                }
            }).finally(() => {
                this.getAll();
            });
        },
        handleEdit() {
            axios.put("/books", this.formData).then(res => {
                if (res.data.flag) {
                    this.dialogFormVisible4Edit = false;
                    this.$message.success("修改成功!!!");
                } else {
                    this.$message.error("修改失败!!!");
                }
            }).finally(() => {
                this.getAll();
            });
        }
    }
};
</script>

<style lang="scss" scoped>
a {

    color: #3c8dbc;

    text-decoration: none;

}

/* new style */

.skin-purple .main-sidebar {

    background: #fff;

}

.skin-purple .main-header .logo:hover {

    background: #0abdfe;

}


.skin-purple .main-header {

    min-height: 70px;

    padding: 0;

}

.skin-purple .main-header .logo {

    height: 50px;

    /* background: #0abdfe; */

    float: left;

    padding: 20px 0 0 15px;

    /* width: 230px; */

}

.skin-purple .main-header .navbar {

    height: 70px;

    background: linear-gradient(to right, #0abdfe, #67f0e0);

    /* margin-left: 230px; */

}

.winfo {
    margin-left: 230px;
}

.skin-purple .main-header .sidebar-toggle {

    display: inline-block;

    padding: 24px 15px;

    color: #fff;

}

.skin-purple .main-sidebar {

    padding-top: 75px;

}

.sidebar-menu>li {

    line-height: 1.8
}

.skin-purple .sidebar-menu>li>a {

    font-size: 16px;

    color: #666
}

.skin-purple .sidebar-menu>li:hover>a,
.skin-purple .sidebar-menu>li.active>a {

    background: transparent;

    color: #666;

    border-left-color: transparent
}

.skin-purple .treeview-menu>li>a:hover {

    color: #fff
}

.skin-purple .sidebar-menu>li>.treeview-menu {

    background: #fff;

}

.sidebar-menu .treeview-menu>li>a {

    font-size: 16px;

    padding-left: 35px;

    color: #999
}

.sidebar-menu .treeview-menu>li:hover {

    background: #0abdfe;

}

@media (min-width: 768px) {

    .skin-purple .navbar-nav>li>a {

        padding-top: 25px;

        padding-bottom: 25px;

    }

}

.modal-body .nav-tabs>li.active>a,
.nav-tabs>li.active>a:focus,
.nav-tabs>li.active>a:hover {

    color: #0abdfe
}

.modal-body .nav-tabs>li>a {

    color: #555
}

.bg-olive {

    background-color: #0abdfe !important;

}

.dataTable .btn[class*='bg-']:hover {

    box-shadow: none
}

.btn-primary {

    background: #0abdfe;

    border-color: #0abdfe;

}

.box-body .nav>li>a {

    color: #666
}

.box-body .nav>li.active>a {

    color: #0abdfe;

}


/* tab 1*/

.double {

    line-height: 58px;

}

.title .glyphicon {

    padding: 3px;

    font-size: 13px;

    border-radius: 8px;

    color: #fff;


}

.data span.arrowup {

    color: #d88918;

}

.data span.arrowdown {

    color: #6bb10a;

}

.item-blue .glyphicon {

    background-color: #39a9ea;

}

.item-green {

    line-height: 58px;

}

.item-green .glyphicon {

    background-color: #6bb10a;

    line-height: 12px;

}

.item-orange .glyphicon {

    background-color: #d88918;

}

.item-red .glyphicon {

    background-color: #f14f4f;

}

.chart .chart-box {

    margin: 10px;

}


/* 数据表格label */

.content-wrapper .data-type {

    /*width: 90%;*/

    margin: 10px 5px;

    border: 1px solid #d4d4d4;

    border-radius: 2px;

}

.data-type .title,
.data-type .data {

    padding: 3px 12px;

    border-top: 1px solid #d4d4d4;

    overflow: hidden;

    height: 42px;

}

.data-type .title {

    line-height: 34px;

    border-right: 1px solid #d4d4d4;

}


.data-type .data:last-child {

    border-right: 0;

}

.data-type .title {

    text-align: center;

    background: #ececec;

}

.data-type .data .line {

    vertical-align: middle;

    overflow: hidden;

    padding-bottom: 10px;

    padding-top: 10px;

}


/* label行高度 */

.data-type .data>label {

    line-height: 36px;

}

.data-type .data>.form-group {

    line-height: 36px;

}

.data-type .data.text {

    line-height: 36px;

}

/* label行分隔符 */

.data-type .data.border-right {

    border-right: 1px solid #d4d4d4;

}


/* 表格双倍高度 */

.data-type .title.rowHeight2x,
.data-type .data.rowHeight2x {

    height: 84px;

}

.data-type .title.rowHeight2x,
.data-type .data.rowHeight2x.text {

    line-height: 78px;

}

/*.data-type .data.rowHeight2x > label {

line-height:78px;

}*/

.data-type .title.editer,
.data-type .data.editer {

    height: 320px;

}

.data-type .title.editer {

    line-height: 300px;

}


/*清除parding*/

.padding-clear {

    padding-right: 0px;

    padding-left: 0px;

}


/* 文件上传 */

/*a  upload */

.a-upload {

    padding: 4px 10px;

    height: 35px;

    line-height: 25px;

    position: relative;

    cursor: pointer;

    color: #888;

    background: #fafafa;

    border: 1px solid #ddd;

    border-radius: 4px;

    overflow: hidden;

    display: inline-block;

    *display: inline;

    *zoom: 1
}

.a-upload input {

    position: absolute;

    font-size: 100px;

    right: 0;

    top: 0;

    opacity: 0;

    filter: alpha(opacity=0);

    cursor: pointer
}

.a-upload:hover {

    color: #444;

    background: #eee;

    border-color: #ccc;

    text-decoration: none
}

/* 医疗 */

.search-box {

    display: inline-block
}

.input-sm {

    height: 32px;

}

.btn-create {

    margin-left: 10px;

    background-color: #0abdfe;

    border-color: #0abdfe;

    color: #fff;

}

.btn-create:hover,
.btn-create:active,
.btn-create:focus {

    color: #fff;

}

.pagination {

    margin: 0
}

.medical-modal {

    position: absolute;

    top: 0%;

    left: 0%;

    display: none;

    background: rgba(0, 0, 0, 0.3);

    width: 100%;

    height: 100%;

    position: fixed;

    z-index: 9999
}

.medical-modal .content {

    position: absolute;

    left: 35%;

    top: 25%;

    border-radius: 8px;

    width: 30%;

    height: 40%;

    background-color: #fff;

}

.pageitems,
.jump {

    margin-left: 15px;

    display: inline-block;

}

.jumppage {

    width: 30px;

    text-align: center
}

@media (min-width: 768px) {

    .subscribe .modal-dialog {

        width: 900px;

        margin: 30px auto;

    }

}

.checklist {

    margin-top: 10px;

}

.checklist .input-group {

    margin-bottom: 10px;

}

.modal-page {

    margin-top: 20px;

    font-size: 12px;

}

.modal-page .form-control {

    font-size: 12px;

    padding: 0;

    height: 26px;

}

.table-check {

    margin: 0;

    display: inline-block;

    margin-right: 4px;

}

.daterange {

    margin: 10px 10px 0;

}

.daterange .input-group .form-control {

    width: 20%;

}

.chart-title {

    font-size: 16px;

    font-weight: normal;

    text-align: center;

}

.diaocha {

    line-height: 2
}

.diaocha h5 {

    color: #f98d45;

    background: #f5f7f9;

    line-height: 2;

    padding-left: 15px;

}

.diaocha div {

    padding: 0 20px;

    border-bottom: 1px solid #dce1e7;

}

.diaocha div h5 {

    color: #555;

    background: transparent;

    padding-left: 0;

}

.diaocha label {

    font-weight: normal;

}

.diaocha .form-group {

    margin-left: 0;

    margin-right: 0;

}

.diaocha .options label {

    margin-right: 10px;

}


.tizhi button {

    margin-right: 15px;

}

.innerform {

    margin-top: 20px;

}

.fa-search {

    cursor: pointer
}

.line {

    margin-top: 10px;

}

input[type=radio]:focus {

    outline: none
}

input[type="radio"] {

    appearance: none;

    -webkit-appearance: none;

    outline: none;

    display: none
}

label input[type="radio"] {

    content: "\a0";

    display: inline-block;

    vertical-align: middle;

    font-size: 16px;

    width: 15px;

    height: 15px;

    margin-right: .4em;

    border-radius: 50%;

    border: 1px solid #c7c6c6;

    line-height: 1;

    margin-top: -1px;

}

label input[type="radio"]:checked {

    border: 3px solid #0abdfe;

}

.right-menu {

    float: right;

    padding: 18px 30px 0 0;

    color: #fff;

}

.el-dropdown {
    color: #fff;
}

.avatar-wrapper img {
    width: 30px;
    height: 30px;
    border-radius: 15px;
    vertical-align: middle
}

.el-popper[x-placement^=bottom] {
    margin-top: 30px;
}

.el-dropdown-menu__item--divided {
    margin: 0;
    border: 0 none;
    border-bottom: 1px solid #ebeef5
}

.help {

    padding: 0 10px;

}

.help .fa {
    margin-right: 5px;
}

.el-main {

    background: #ecf0f5;

}

.el-menu {
    border: 0 none;
}

.main {

    height: 100vh;

    min-width: 800px;

    min-height: 600px;

    overflow: hidden;

}

.main aside {

    overflow: visible;

    height: 100%;

}

.main aside.isClossTab {

    width: 100%;

    height: 60px;

    cursor: pointer;

    font-size: 25px;

    text-align: center;

    line-height: 60px;

    font-weight: bold;

    border-right: 1px solid #807c7c;

    box-sizing: border-box;

}

.main aside .menu {

    width: 100%;

    border-right: 0;

}

.el-menu .fa {

    vertical-align: middle;

    margin-right: 5px;

    width: 24px;

    text-align: center;

    font-size: 18px;

}

.el-menu-item a {

    color: #303133
}

.el-menu-item:hover,
.el-menu-item.is-active {

    color: #fff;

    background: #0abdfe;

}

.el-menu-item:hover a,
.el-menu-item.is-active a {

    color: #fff;

}

.el-submenu__title:hover {
    background: none;
}

.main-footer {

    background: #fff;

    padding: 15px 0;

    color: #444;

}

/* title */

.content-header {

    position: relative;

    padding: 5px 15px 0 15px;

    /* margin-top: 70px; */

}

.content-header>h1 {

    margin: 0;

    font-size: 24px;

    font-weight: normal;

}

.content-header>h1>small {

    font-size: 15px;

    display: inline-block;

    padding-left: 4px;

    font-weight: 300;

}

.content-header>.breadcrumb {

    float: right;

    background: transparent;

    margin-top: 0;

    margin-bottom: 0;

    font-size: 12px;

    padding: 7px 5px;

    position: absolute;

    top: 20px;

    right: 10px;

    border-radius: 2px;

}

/*  */

.app-container {

    background: #fff;

    margin: 10px 20px 15px 15px;


}

.pagiantion {

    text-align: right;

    padding: 15px;

}

.box {

    position: relative;

    border-radius: 3px;

    background: #ffffff;

    border-top: 3px solid #3c8dbc;

    padding: 10px;

    margin-bottom: 20px;

    width: 100%;

    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);

}

.filter-container {

    padding: 10px 0 15px 0;

}

.main-container {
    margin-top: 70px;
}

.filter-container .el-button,
.filter-container .el-input__inner {

    padding: 0 15px;

    height: 34px;

    line-height: 34px;

}

.el-aside {
    overflow: hidden;
}

.el-submenu .el-menu-item a {

    display: block;

    height: 50px;

}

.el-menu--collapse .el-submenu__icon-arrow {
    display: none
}

/* .el-container{position: relative;} */

/* foot */

.el-footer {

    position: absolute;

    left: 180px;

    right: 0px;

    bottom: -80px;

}

.boxMain .el-upload--text {

    position: static;

}

.boxMain>div {

    display: inline-block;

}

.excelTitle {

    text-align: center;

    overflow: hidden;

    line-height: 40px;

}

.excelTitle .el-button {

    float: left;

}

.excelTime {

    padding: 10px 0;

    text-align: right;

}

.exceTable {

    width: 100%;

    border-right: 1px solid #e6e6e6;

    border-bottom: 1px solid #e6e6e6;

    font-size: 14px;

    color: #333;

}

.exceTable tr,
.exceTable td {

    border-left: 1px solid #e6e6e6;

    border-top: 1px solid #e6e6e6;

    height: 40px;

    line-height: 40px;

    padding: 0 10px;

}

.exceTable .headBody {

    text-align: center;

    font-weight: 700;

    font-size: 14px;

}

.tabletrBg {

    background: #fcfcfc;

    text-align: right;

}

.textCenter {

    text-align: center
}

.checkScrol {

    height: 277px;

    overflow-y: scroll;
    ;

}
</style>