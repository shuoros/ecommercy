/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CommentDetailComponent from '@/entities/comment/comment-details.vue';
import CommentClass from '@/entities/comment/comment-details.component';
import CommentService from '@/entities/comment/comment.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Comment Management Detail Component', () => {
    let wrapper: Wrapper<CommentClass>;
    let comp: CommentClass;
    let commentServiceStub: SinonStubbedInstance<CommentService>;

    beforeEach(() => {
      commentServiceStub = sinon.createStubInstance<CommentService>(CommentService);

      wrapper = shallowMount<CommentClass>(CommentDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { commentService: () => commentServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundComment = { id: 123 };
        commentServiceStub.find.resolves(foundComment);

        // WHEN
        comp.retrieveComment(123);
        await comp.$nextTick();

        // THEN
        expect(comp.comment).toBe(foundComment);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundComment = { id: 123 };
        commentServiceStub.find.resolves(foundComment);

        // WHEN
        comp.beforeRouteEnter({ params: { commentId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.comment).toBe(foundComment);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
