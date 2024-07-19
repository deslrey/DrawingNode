<template>
    <div class="box-main2 box-heigt100">
        <div class="box-search">
            <div>
                <el-input type="text" size="mini" @keydown.native.enter="clkBtnSearch" @clear="clkBtnSearch"
                    v-model="searchInfo.searchKey" clearable placeholder="请输入标题"></el-input>
            </div>
            <div class="m1">
                <el-button type="primary" size="mini" @click="clkBtnSearch">搜索</el-button>
            </div>
            <div class="m1">
                <el-button type="warning" size="mini" @click="clkBtnAdd">新增</el-button>
            </div>
        </div>
        <div class="box-table2">
            <el-table :data="filePage.list" border style="width: 100%;">
                <el-table-column type="index" label="序号" width="120"></el-table-column>
                <el-table-column width="150" label="文件">
                    <i class="el-icon-document"></i>
                </el-table-column>
                <el-table-column prop="fileName" label="文件名"></el-table-column>
                <el-table-column label="上传日期">
                    <template slot-scope="scope">
                        {{ formatDate(scope.row.createTime) }}
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="warning" @click="clkBtnEdit(scope.row)">修改</el-button>
                        <el-button type="danger" @click="clkBtnDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="box-page1">
            <el-pagination background :current-page="filePage.pageNum" :page-size="filePage.pageSize"
                :total="filePage.total" layout="total,prev,pager,next" @current-change="chgPageNum">
            </el-pagination>
        </div>
        <el-dialog title="新增/修改文件信息" :visible.sync="dialogVisible1">
            <el-form ref="importFormRef" :model="importForm" :rules="rules" label-width="130px">
                <el-form-item label="文件名" prop="fileName">
                    <el-input v-model="importForm.fileName"></el-input>
                </el-form-item>
                <el-form-item label="上传文件:" prop="excel">

                    <el-upload ref="uploadTxt" style="display: inline;" action :show-file-list="false"
                        :http-request="uploadFile" :file-list="fileList" :limit="1" :on-exceed="handleExceed"
                        :before-upload="beforeUpload" :onError="uploadError">
                        <el-button icon="el-icon-upload" type="primary">文件上传
                        </el-button>
                    </el-upload>

                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitImportForm">确认</el-button>
                    <el-button type="info" @click="dialogVisible1 = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>


    </div>
</template>

<script>
import uploadFiles from '@/api/file/uploadFiles'
import moment from 'moment'

export default {

    name: 'UploadFiles',
    data() {
        return {
            searchInfo: { searchKey: '' },
            filePage: { pageNum: 1, pageSize: 10, list: [] },
            picture: {},
            uploadUrl: '',
            uploadDataParam: {},
            src_url: 'http://127.0.0.1:81/deslre/uploadFile/upload',
            editPicture: false,
            //对话框控制权
            dialogVisible1: false,
            //导入表单数据
            importForm: {
                fileName: '',
            },
            uploadUrl: 'http://localhost:8080/upload',
            //存放上传文件
            fileList: [],
            rules: {
                fileName: [{ required: true, message: '请输入名称', trigger: 'blur' }],
                excel: [{ required: true, message: '请上传文件', trigger: 'blur' }],
            }
        };
    },
    watch: {
        '$route.path': {
            handler: function (newVal) {
                if (newVal == '/file/uploadFiles') {
                    this.initData();
                }
            }, immediate: true
        },
    },
    mounted() {
        this.uploadUrl = 'http://localhost:81/deslre/uploadFile/upload';
        // this.initData();
    },
    methods: {
        initData() {
            this.getPictureList();
        },
        getPictureList() {
            let data = {};
            data.searchKey = this.searchInfo.searchKey;
            data.pageNum = this.filePage.pageNum;
            data.pageSize = this.filePage.pageSize;

            console.log('getPictureList ------> ', data);

            uploadFiles.getPictureList(data).then((response) => {

                this.filePage = response.data
                console.log('getPictureList ------> ', this.filePage);
            })
        },
        clkBtnAdd() {
            this.picture = {};
            this.dialogVisible1 = true;
        },
        clkBtnEdit(row) {
            this.picture = JSON.parse(JSON.stringify(row));
            this.editPicture = true;
            this.dialogVisible1 = true;
        },

        //保存表单提交
        submitUpload() {
            console.log('submitUpload ------> ', this.uploadDataParam);
            this.$refs.upload.submit();

        },
        clkBtnDelete(row) {
            this.$confirm("您确信要删除吗？", "提示").then(() => {
                let data = { id: row.id };
                console.log('clkBtnDelete ------> ', this.data);
                uploadFiles.clkBtnDelete(data).then(response => {
                    this.getPictureList();
                    this.$message({
                        message: "删除成功~",
                        type: 'success'
                    })
                })

            }).catch(() => {
                this.$message({
                    message: "取消删除~",
                    type: 'warning'
                })
            })
        },
        chgPageNum(pageNum) {
            this.filePage.pageNum = pageNum;
            this.getPictureList();
        },
        clkBtnSearch() {
            this.getPictureList();
        },
        formatDate(date) {
            return moment(date).format('YYYY-MM-DD HH:mm:ss');
        },

        //限制文件上传的数量，:limit="1"
        handleExceed(files, fileList) {
            this.$message.error("文件超出限制，请重试！");
        },

        //上传失败提示
        uploadError(response, file, fileList) {
            this.$message.error("上传失败，请重试！");
        },

        //上传前限制文件类型，文件大小
        beforeUpload(file) {
            let namestrarr = file.name.split(".");
            let fileLastname = namestrarr[namestrarr.length - 1].toLowerCase();
            const extension = fileLastname === "txt";
            const isLt2M = file.size / 1024 / 1024 < 20;
            if (!extension) {
                this.$message.warning("上传文件只能是 txt 格式!");
            }
            if (!isLt2M) {
                console.log("上传附件大小不能超过 2MB!");
                this.$message.warning("上传附件大小不能超过 2MB!");
            }
            this.uploadFileName = file.name;
            this.fileSuffixType = fileLastname;
            return isLt2M && extension;
        },

        //上传文件
        uploadFile(item) {
            let formData = new FormData();
            formData.append("files", item.file);
            uploadFiles.addFile(formData).then(response => {
                this.$refs.uploadTxt.clearFiles();  //清空上传的文件缓存
                this.parsingFile(response.data.filePath)//文件数据解析入库，如果只是上传文件就不需要
            })
                .catch(err => {
                    this.$refs.uploadTxt.clearFiles();
                    this.$message.error(err);
                })
        },

        //文件解析入库
        parsingFile(path) {
            uploadFiles.importData({ path: path }).then(response => {
                if (response.data === true) {
                    this.$message.success("数据导入成功");
                } else {
                    this.$message.error("数据导入失败！");
                }
            })
        },
    },
};
</script>

<style lang="scss" scoped="scoped">
.box-main2 {
    width: 90%;
    margin: 10px;

    box-table2 {
        height: auto;
    }

    .box-search {
        display: flex;
        margin: 10px;

        .m1 {
            padding-left: 10px;
        }
    }

    .box-page1 {
        margin-top: 10px;
    }
}
</style>