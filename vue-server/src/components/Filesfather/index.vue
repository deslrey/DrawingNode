<template>
    <!--功能(单个文件)：1、上传文件校验 2、随表单一块提交 3、文件列表展示 4、列表删除后文件联动 -->
    <el-dialog title="提示" :visible="dialogVisible" :close-on-click-modal="false" :close-on-press-escape="false"
        @update:visible="changeVisible()" width="30%">
        <el-form ref="form" :model="form" label-width="80px" :rules="rules">
            <el-form-item label="名称" prop="name">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="上传文件" prop="file">
                <el-upload class="upload-demo" drag action="" accept="*" :on-change="handleChange" :auto-upload="false"
                    :file-list="form.file" :on-remove="handleAddRemove">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                    <div class="el-upload__tip" slot="tip">只能上传txt/jpg文件，且不超过5kb</div>
                </el-upload>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="changeVisible()">取 消</el-button>
            <el-button type="primary" @click="save()">确 定</el-button>
        </span>
    </el-dialog>

</template>

<script>
export default {
    name: "Filesfather",
    props: ['dialogVisible'],
    data() {
        return {
            form: {
                name: '',
                file: []
            },
            rules: {
                name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
                file: [{ required: true, validator: this.validateFile }],
            }

        }
    },
    methods: {
        resetForm() {
            this.$refs.form.resetFields();
        },
        // 取消
        changeVisible(value = false) {
            this.$emit('update:dialogVisible', value)
        },
        // 改变文件
        handleChange(file, fileList) {
            // 个人理解：this.form.file是已有列表，fileList是已有列表加上新加的文件，需手动赋值处理；
            // 如果不处理，已有列表会自动显示fileList（检测到this.form.file变化就显示this.form.file，否则显示fileList）
            this.form.file = [file];
            this.$refs.form.validateField('file', (err) => {
                if (err) {
                    //文件校验不通过，列表删除最后一个
                    this.form.file = fileList;
                    this.form.file.splice(-1, 1);
                }
            });
        },
        //移除文件
        handleAddRemove(file, fileList) {
            //仅支持单个文件时
            this.form.file = fileList;
        },
        save() {
            if (!this.validate()) return;
            this.$emit('save', this.form)
        },
        // 自定义文件校验规则
        validateFile(rule, value, callback) {
            let file = this.form.file[0];
            if (!file) {
                callback(new Error('请上传文件'));
                return false;
            }
            //file格式及大小校验
            // const validName = file.name.endsWith('.txt') || file.name.endsWith('.jpg');
            const validName = true;
            const validSize = file.size < (1024 * 50000);
            if (!validName) {
                callback(new Error('文件类型不正确'));
            } else if (!validSize) {
                callback(new Error('文件大小超出限制'));
            } else {
                callback();
            }
        },
        validate() {
            let result = false;
            this.$refs.form.validate((valid) => {
                result = valid;
            })
            return result;
        }
    }
}
</script>

<style lang="less" scoped></style>
