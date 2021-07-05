/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TorreDetailComponent from '@/entities/torre/torre-details.vue';
import TorreClass from '@/entities/torre/torre-details.component';
import TorreService from '@/entities/torre/torre.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Torre Management Detail Component', () => {
    let wrapper: Wrapper<TorreClass>;
    let comp: TorreClass;
    let torreServiceStub: SinonStubbedInstance<TorreService>;

    beforeEach(() => {
      torreServiceStub = sinon.createStubInstance<TorreService>(TorreService);

      wrapper = shallowMount<TorreClass>(TorreDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { torreService: () => torreServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTorre = { id: 123 };
        torreServiceStub.find.resolves(foundTorre);

        // WHEN
        comp.retrieveTorre(123);
        await comp.$nextTick();

        // THEN
        expect(comp.torre).toBe(foundTorre);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTorre = { id: 123 };
        torreServiceStub.find.resolves(foundTorre);

        // WHEN
        comp.beforeRouteEnter({ params: { torreId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.torre).toBe(foundTorre);
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
