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
        <el-dialog :visible.sync="showPictureDigLog" title="新增/编辑">
            <el-form label-width="120px">
                <el-form-item label="标题">
                    <el-input type="text" size="mini" placeholder="请输入标题" v-model="picture.file_name"></el-input>
                </el-form-item>
                <el-form-item label="文件">
                    <el-image v-if="editPicture" style="width: 200px" :src="src_url + picture.relative_path"></el-image>
                    <el-upload class="upload-demo" ref="upload" :action="uploadUrl" :data="uploadDataParam"
                        :before-upload="checkFileType" accept="*" :auto-upload="false">
                        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传不超过5M</div>
                    </el-upload>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" size="mini" @click="clkBtnSave">确定</el-button>
                <el-button type="warning" size="mini" @click="showPictureDigLog = false">取消</el-button>
            </span>
        </el-dialog>


    </div>
</template>

<script>
import uploadFiles from '@/api/uploadFiles'
import moment from 'moment'

export default {

    name: 'UploadFiles',
    data() {
        return {
            searchInfo: { searchKey: '' },
            filePage: { pageNum: 1, pageSize: 2, list: [] },
            showPictureDigLog: false,
            picture: {},
            uploadUrl: '',
            uploadDataParam: {},
            src_url: 'http://127.0.0.1:8345/springbootajax/',
            editPicture: false
        };
    },
    watch: {
        '$route.path': {
            handler: function (newVal) {
                if (newVal == '/picture-list') {
                    this.initData();
                }
            }, immediate: true
        },
    },
    mounted() {
        this.uploadUrl = 'http://localhost:81/deslre/uploadFile/upload';
        this.initData();
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
            this.showPictureDigLog = true;
        },
        clkBtnEdit(row) {
            this.picture = JSON.parse(JSON.stringify(row));
            this.editPicture = true;
            this.showPictureDigLog = true;
        },
        checkFileType(file) {
            let imgSize = file.size / 1024 / 1024;
            console.log("文件类型:" + file.type);
            console.log("文件大小：" + imgSize)
            if (imgSize > 5) {
                this.$message("文件超出规定上传大小，请重新上传文件！")
                return false;
            }
            //png/jpg/gif/bmp
        },
        clkBtnSave() {
            let data = this.picture;
            uploadFiles.clkBtnSave(data).then(response => {
                this.uploadDataParam.noid = response.data;
                this.submitUpload();
                this.showPictureDigLog = false;
                this.getPictureList();
                this.$message("保存成功~")
            })
        },

        //保存表单提交
        submitUpload() {
            this.$refs.upload.submit();

        },
        clkBtnDelete(row) {
            this.$confirm("您确信要删除吗？", "提示").then(() => {
                let data = { noid: row.noid };

                uploadFiles.clkBtnDelete(data).then(response => {
                    this.getPictureList();
                    this.$message("删除成功~");
                })

            }).catch(() => {
                this.$message("取消删除~");
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
        }
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